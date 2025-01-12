package com.ai.bardly.di

import com.ai.bardly.analytics.Analytics
import com.ai.bardly.analytics.AnalyticsManager
import com.ai.bardly.analytics.DebugAnalyticsManager
import com.ai.bardly.buildconfig.BuildConfig
import com.ai.bardly.buildconfig.getBuildConfig
import com.ai.bardly.data.GamesApi
import com.ai.bardly.data.GamesRepository
import com.ai.bardly.data.KtorGamesApi
import com.ai.bardly.navigation.NavigationManager
import com.ai.bardly.networking.getHttpEngine
import com.ai.bardly.screens.chats.list.ChatsViewModel
import com.ai.bardly.screens.games.details.GameDetailsViewModel
import com.ai.bardly.screens.chats.details.ChatsDetailsViewModel
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
import org.koin.dsl.module

val dataModule = module {
    single {
        val json = Json { ignoreUnknownKeys = true }
        HttpClient(get<HttpClientEngineFactory<*>>()) {
            install(ContentNegotiation) {
                // TODO Fix API so it serves application/json
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

    single<GamesApi> { KtorGamesApi(get(), get()) }
    single {
        GamesRepository(get())
    }
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

fun initKoin() {
    startKoin {
        modules(
            dataModule,
            viewModelModule,
            analyticsModule,
            buildConfigModule,
            navigationModule,
        )
    }
}
