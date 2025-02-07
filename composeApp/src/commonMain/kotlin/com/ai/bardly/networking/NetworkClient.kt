package com.ai.bardly.networking

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.headers
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.statement.HttpResponse
import io.ktor.client.statement.bodyAsText
import io.ktor.http.ContentType
import io.ktor.http.HeadersBuilder
import io.ktor.http.URLBuilder
import io.ktor.http.contentType
import io.ktor.http.isSuccess
import io.ktor.http.path
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.ensureActive
import kotlinx.coroutines.withContext
import org.koin.core.annotation.Single

@Single
class NetworkClient(
    @PublishedApi
    internal val httpClient: HttpClient,
) {

    suspend inline fun <reified T> get(
        path: String,
        noinline queryBuilder: QueryParamsScope.() -> Unit = {},
        noinline headers: HeadersBuilder.() -> Unit = {},
    ): Result<T> = performRequest {
        val url = buildUrl(path, queryBuilder)
        httpClient.get(url) {
            headers(headers)
        }
    }

    suspend inline fun <reified T> post(
        path: String,
        body: Any? = null,
        noinline queryBuilder: QueryParamsScope.() -> Unit = {},
        noinline headers: HeadersBuilder.() -> Unit = {},
    ): Result<T> = performRequest {
        val url = buildUrl(path, queryBuilder)
        httpClient.post(url) {
            headers(headers)
            setBody(body)
            contentType(ContentType.Application.Json)
        }
    }

    @PublishedApi
    internal fun buildUrl(path: String, queryBuilder: QueryParamsScope.() -> Unit): String {
        val queryParamsScope = QueryParamsScope()
        queryBuilder(queryParamsScope)

        val urlBuilder = URLBuilder(BASE_URL).apply {
            path(path)
            queryParamsScope.params.forEach { (key, value) ->
                when (value) {
                    is String -> parameters.append(key, value)
                    is Number -> parameters.append(key, value.toString())
                    is Boolean -> parameters.append(key, value.toString())
                    is List<*> -> value.forEach { item ->
                        if (item != null) {
                            parameters.append(key, item.toString())
                        }
                    }

                    null -> Unit  // Ignore null parameters
                    else -> parameters.append(key, value.toString()) // Default for others
                }
            }
        }
        return urlBuilder.buildString()
    }

    @PublishedApi
    internal suspend inline fun <reified T> performRequest(
        crossinline block: suspend () -> HttpResponse
    ): Result<T> = withContext(Dispatchers.IO) {
        try {
            val response = block()
            if (response.status.isSuccess()) {
                Result.success(response.body())
            } else {
                Result.failure(Exception("HTTP error ${response.status}: ${response.bodyAsText()}"))
            }
        } catch (e: Exception) {
            ensureActive()
            // Already logged in http client
            Result.failure(e)
        }
    }

    protected fun closeClient() {
        httpClient.close()
    }

    companion object {
        private const val BASE_URL = "https://dolphin-app-zeoxd.ondigitalocean.app"
    }
}