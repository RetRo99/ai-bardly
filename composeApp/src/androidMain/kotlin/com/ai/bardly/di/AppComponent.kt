package com.ai.bardly.di

import android.app.Application
import com.ai.bardly.analytics.Analytics
import com.ai.bardly.networking.getHttpEngine
import io.ktor.client.HttpClient
import io.ktor.client.engine.HttpClientEngineFactory
import io.ktor.client.plugins.HttpResponseValidator
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.http.ContentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import me.tatarka.inject.annotations.Provides
import software.amazon.lastmile.kotlin.inject.anvil.AppScope
import software.amazon.lastmile.kotlin.inject.anvil.MergeComponent
import software.amazon.lastmile.kotlin.inject.anvil.SingleIn

@MergeComponent(AppScope::class)
@SingleIn(AppScope::class)
abstract class AppComponent(
    @get:Provides val application: Application,
) : ActivityComponent.Factory {
    abstract val activityComponentFactory: ActivityComponent.Factory

    // region Networking TODO: Move to a separate module
    @Provides
    @SingleIn(AppScope::class)
    fun provideHttpClientEngineFactory(): HttpClientEngineFactory<*> = getHttpEngine()

    @Provides
    @SingleIn(AppScope::class)
    fun provideHttpClient(
        httpFactory: HttpClientEngineFactory<*>,
        analytics: Analytics
    ): HttpClient {
        val json = Json { ignoreUnknownKeys = true }
        return HttpClient(httpFactory) {
            install(ContentNegotiation) {
                json(json, contentType = ContentType.Application.Json)
            }
            HttpResponseValidator {
                handleResponseExceptionWithRequest { cause, request ->
                    analytics.logException(cause, request.url.toString())
                }
            }
        }
    }

    //endregion
    companion object
}
