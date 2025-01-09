package com.ai.bardly.di

import com.ai.bardly.analytics.Analytics
import com.ai.bardly.analytics.AnalyticsManager
import com.ai.bardly.analytics.DebugAnalyticsManager
import com.ai.bardly.buildconfig.BuildConfig
import com.ai.bardly.buildconfig.getBuildConfig
import com.ai.bardly.data.InMemoryMuseumStorage
import com.ai.bardly.data.KtorMuseumApi
import com.ai.bardly.data.MuseumApi
import com.ai.bardly.data.MuseumRepository
import com.ai.bardly.data.MuseumStorage
import com.ai.bardly.screens.chats.ChatsViewModel
import com.ai.bardly.screens.games.GamesViewModel
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

    single<MuseumApi> { KtorMuseumApi(get()) }
    single<MuseumStorage> { InMemoryMuseumStorage() }
    single {
        MuseumRepository(get(), get()).apply {
            initialize()
        }
    }
}

val viewModelModule = module {
    factoryOf(::HomeViewModel)
    factoryOf(::GamesViewModel)
    factoryOf(::ChatsViewModel)
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
