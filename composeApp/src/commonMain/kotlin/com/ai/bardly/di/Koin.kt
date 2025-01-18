package com.ai.bardly.di

import com.ai.bardly.analytics.Analytics
import com.ai.bardly.analytics.AnalyticsManager
import com.ai.bardly.analytics.DebugAnalyticsManager
import com.ai.bardly.buildconfig.BuildConfig
import com.ai.bardly.buildconfig.getBuildConfig
import com.ai.bardly.data.chat.ChatsDataRepository
import com.ai.bardly.data.chat.local.ChatsLocalDataSource
import com.ai.bardly.data.chat.local.RoomChatsLocalDataSource
import com.ai.bardly.data.chat.remote.ChatsRemoteDataSource
import com.ai.bardly.data.chat.remote.KtorChatsRemoteDataSource
import com.ai.bardly.data.game.GamesDataRepository
import com.ai.bardly.data.game.local.GamesLocalDataSource
import com.ai.bardly.data.game.local.RoomGamesLocalDataSource
import com.ai.bardly.data.game.remote.GamesRemoteDataSource
import com.ai.bardly.data.game.remote.KtorGamesRemoteDataSource
import com.ai.bardly.database.AppDatabase
import com.ai.bardly.database.getDatabaseModule
import com.ai.bardly.domain.chats.ChatsRepository
import com.ai.bardly.domain.games.GamesRepository
import com.ai.bardly.navigation.NavigationManager
import com.ai.bardly.networking.NetworkClient
import com.ai.bardly.networking.getHttpEngine
import com.ai.bardly.screens.chats.details.ChatsDetailsViewModel
import com.ai.bardly.screens.chats.list.ChatsViewModel
import com.ai.bardly.screens.games.details.GameDetailsViewModel
import com.ai.bardly.screens.games.list.GamesListViewModel
import com.ai.bardly.screens.home.HomeViewModel
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
    single<GamesLocalDataSource> { RoomGamesLocalDataSource(get()) }
    single<GamesRemoteDataSource> { KtorGamesRemoteDataSource(get()) }
    single<GamesRepository> { GamesDataRepository(get(), get()) }
    single { get<AppDatabase>().getGamesDao() }
}

val chatsDataModule = module {
    single<ChatsRemoteDataSource> { KtorChatsRemoteDataSource(get()) }
    single<ChatsLocalDataSource> { RoomChatsLocalDataSource(get()) }
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
    factoryOf(::ChatsViewModel)
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
            getDatabaseModule()
        )
    }
}
