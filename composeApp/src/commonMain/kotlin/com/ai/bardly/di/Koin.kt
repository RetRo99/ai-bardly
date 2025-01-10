package com.ai.bardly.di

import com.ai.bardly.analytics.Analytics
import com.ai.bardly.analytics.AnalyticsManager
import com.ai.bardly.analytics.DebugAnalyticsManager
import com.ai.bardly.buildconfig.BuildConfig
import com.ai.bardly.buildconfig.getBuildConfig
import com.ai.bardly.data.InMemoryGamesStorage
import com.ai.bardly.data.KtorGamesApi
import com.ai.bardly.data.GamesApi
import com.ai.bardly.data.GamesRepository
import com.ai.bardly.data.GamesStorage
import com.ai.bardly.screens.chats.ChatsViewModel
import com.ai.bardly.screens.games.list.GamesListViewModel
import com.ai.bardly.screens.games.details.GameDetailsViewModel
import com.ai.bardly.screens.home.HomeViewModel
import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.analytics.analytics
import io.ktor.client.HttpClient
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.http.ContentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import org.koin.core.context.startKoin
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module

val dataModule = module {
    single {
        val json = Json { ignoreUnknownKeys = true }
        HttpClient {
            install(ContentNegotiation) {
                // TODO Fix API so it serves application/json
                json(json, contentType = ContentType.Any)
            }
        }
    }

    single<GamesApi> { KtorGamesApi(get()) }
    single<GamesStorage> { InMemoryGamesStorage() }
    single {
        GamesRepository(get(), get()).apply {
            initialize()
        }
    }
}

val viewModelModule = module {
    factoryOf(::HomeViewModel)
    factoryOf(::GamesListViewModel)
    factoryOf(::ChatsViewModel)
    factoryOf(::GameDetailsViewModel)
}

val buildConfigModule = module {
    single<BuildConfig> { getBuildConfig() }
}
val analyticsModule = module {
    single { Firebase.analytics }
    single<Analytics> {
        if (get<BuildConfig>().isDebug) DebugAnalyticsManager() else AnalyticsManager(
            get()
        )
    }
}

fun initKoin() {
    startKoin {
        modules(
            dataModule,
            viewModelModule,
            analyticsModule,
            buildConfigModule,
        )
    }
}
