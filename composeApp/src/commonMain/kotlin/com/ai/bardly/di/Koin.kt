package com.ai.bardly.di

import com.ai.bardly.analytics.Analytics
import com.ai.bardly.analytics.AnalyticsManager
import com.ai.bardly.analytics.DebugAnalyticsManager
import com.ai.bardly.buildconfig.BuildConfig
import com.ai.bardly.buildconfig.getBuildConfig
import com.ai.bardly.database.AppDatabase
import com.ai.bardly.database.DaoExecutor
import com.ai.bardly.database.getDatabaseModule
import com.ai.bardly.feature.chats.data.ChatsDataRepository
import com.ai.bardly.feature.chats.data.local.ChatsLocalDataSource
import com.ai.bardly.feature.chats.data.local.RoomChatsLocalDataSource
import com.ai.bardly.feature.chats.data.remote.ChatsRemoteDataSource
import com.ai.bardly.feature.chats.data.remote.NetworkChatsRemoteDataSource
import com.ai.bardly.feature.chats.domain.ChatsRepository
import com.ai.bardly.feature.chats.ui.details.ChatsDetailsViewModel
import com.ai.bardly.feature.chats.ui.list.ChatListViewModel
import com.ai.bardly.feature.games.data.GamesDataRepository
import com.ai.bardly.feature.games.data.local.GamesLocalDataSource
import com.ai.bardly.feature.games.data.local.RoomGamesLocalDataSource
import com.ai.bardly.feature.games.data.remote.GamesRemoteDataSource
import com.ai.bardly.feature.games.data.remote.NetworkGamesRemoteDataSource
import com.ai.bardly.feature.games.domain.GamesRepository
import com.ai.bardly.feature.games.ui.details.GameDetailsViewModel
import com.ai.bardly.feature.games.ui.list.GamesListViewModel
import com.ai.bardly.feature.home.ui.HomeViewModel
import com.ai.bardly.navigation.NavigationManager
import com.ai.bardly.networking.NetworkClient
import com.ai.bardly.networking.getHttpEngine
import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.analytics.analytics
import dev.gitlive.firebase.crashlytics.crashlytics
import io.ktor.client.HttpClient
import io.ktor.client.engine.HttpClientEngineFactory
import io.ktor.client.plugins.HttpResponseValidator
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.http.ContentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import org.koin.core.context.startKoin
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.KoinAppDeclaration
import org.koin.dsl.module

val gamesDataModule = module {
    single<GamesLocalDataSource> { RoomGamesLocalDataSource(get(), get()) }
    single<GamesRemoteDataSource> { NetworkGamesRemoteDataSource(get()) }
    single<GamesRepository> { GamesDataRepository(get(), get()) }
    single { get<AppDatabase>().getGamesDao() }
}

val chatsDataModule = module {
    single<ChatsRemoteDataSource> { NetworkChatsRemoteDataSource(get()) }
    single<ChatsLocalDataSource> { RoomChatsLocalDataSource(get(), get()) }
    single<ChatsRepository> { ChatsDataRepository(get(), get()) }
    single { get<AppDatabase>().getMessagesDao() }
}

val networkingModule = module {
    single {
        val json = Json { ignoreUnknownKeys = true }
        HttpClient(get<HttpClientEngineFactory<*>>()) {
            install(ContentNegotiation) {
                json(json, contentType = ContentType.Application.Json)
            }
            HttpResponseValidator {
                handleResponseExceptionWithRequest { cause, request ->
                    get<Analytics>().logException(cause, request.url.toString())
                }
            }
        }
    }
    single<HttpClientEngineFactory<*>> { getHttpEngine() }
    single { NetworkClient(get()) }
}

val viewModelModule = module {
    factoryOf(::HomeViewModel)
    factoryOf(::GamesListViewModel)
    factoryOf(::ChatListViewModel)
    factoryOf(::GameDetailsViewModel)
    factoryOf(::ChatsDetailsViewModel)
}

val buildConfigModule = module {
    single<BuildConfig> { getBuildConfig() }
}

val navigationModule = module {
    single { NavigationManager() }
}
val analyticsModule = module {
    single { Firebase.analytics }
    single { Firebase.crashlytics }
    single<Analytics> {
        if (get<BuildConfig>().isDebug) DebugAnalyticsManager() else AnalyticsManager(
            get(),
            get()
        )
    }
}

val daoModule = module {
    single { DaoExecutor(get()) }
}

fun initKoin(
    appDeclaration: KoinAppDeclaration = {}
) {
    startKoin {
        appDeclaration()
        modules(
            gamesDataModule,
            viewModelModule,
            analyticsModule,
            buildConfigModule,
            navigationModule,
            networkingModule,
            chatsDataModule,
            getDatabaseModule(),
            daoModule,
        )
    }
}
