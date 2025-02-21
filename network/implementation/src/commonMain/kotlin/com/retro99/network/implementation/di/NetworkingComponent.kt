package com.retro99.network.implementation.di

import com.retro99.network.implementation.getHttpEngine
import io.ktor.client.HttpClient
import io.ktor.client.engine.HttpClientEngineFactory
import io.ktor.client.plugins.HttpResponseValidator
import io.ktor.client.plugins.auth.Auth
import io.ktor.client.plugins.auth.providers.BearerTokens
import io.ktor.client.plugins.auth.providers.bearer
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.http.ContentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import me.tatarka.inject.annotations.Provides
import retro99.network.api.tokens.BearerTokenProvider
import retro99.network.api.tokens.BearerTokenRefresher
import software.amazon.lastmile.kotlin.inject.anvil.AppScope
import software.amazon.lastmile.kotlin.inject.anvil.ContributesTo
import software.amazon.lastmile.kotlin.inject.anvil.SingleIn

@ContributesTo(AppScope::class)
interface NetworkingComponent {

    @Provides
    @SingleIn(AppScope::class)
    fun provideHttpClientEngineFactory(): HttpClientEngineFactory<*> = getHttpEngine()

    @Provides
    @SingleIn(AppScope::class)
    fun provideJson(): Json = Json { ignoreUnknownKeys = true }

    @Provides
    @SingleIn(AppScope::class)
    fun provideHttpClient(
        httpFactory: HttpClientEngineFactory<*>,
//        analytics: Analytics,
        json: Json,
        tokenProvider: BearerTokenProvider,
        tokenRefresher: BearerTokenRefresher,
    ): HttpClient {
        return HttpClient(httpFactory) {
            install(ContentNegotiation) {
                json(json, contentType = ContentType.Application.Json)
            }
            install(Auth) {
                bearer {
                    loadTokens {
                        val token = tokenProvider.getBearerToken()
                        token?.let { BearerTokens(it, "") }
                    }
                    refreshTokens {
                        val token = tokenRefresher.refreshBearerToken()
                        token?.let { BearerTokens(it, "") }
                    }
                }
            }
            HttpResponseValidator {
                handleResponseExceptionWithRequest { cause, request ->
//                    analytics.logException(cause, request.url.toString())
                }
            }
        }
    }
}
