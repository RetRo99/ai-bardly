package com.retro99.network.implementation

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.delete
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
import io.ktor.util.reflect.TypeInfo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.ensureActive
import kotlinx.coroutines.withContext
import me.tatarka.inject.annotations.Inject
import retro99.games.api.NetworkClient
import retro99.games.api.QueryParamsScope
import software.amazon.lastmile.kotlin.inject.anvil.AppScope
import software.amazon.lastmile.kotlin.inject.anvil.ContributesBinding
import software.amazon.lastmile.kotlin.inject.anvil.SingleIn
import kotlin.reflect.KClass

@Inject
@SingleIn(AppScope::class)
@ContributesBinding(AppScope::class)
class KtorNetworkClient(
    @PublishedApi
    internal val httpClient: HttpClient,
) : NetworkClient {

    override suspend fun <T : Any> getWithClass(
        path: String,
        type: KClass<T>,
        queryBuilder: QueryParamsScope.() -> Unit,
        headers: HeadersBuilder.() -> Unit
    ): Result<T> = performRequest(type) {
        val url = buildUrl(path, queryBuilder)
        httpClient.get(url) {
            headers(headers)
        }
    }

    override suspend fun <T : Any> postWithClass(
        path: String,
        type: KClass<T>,
        body: Any?,
        queryBuilder: QueryParamsScope.() -> Unit,
        headers: HeadersBuilder.() -> Unit
    ): Result<T> = performRequest(type) {
        val url = buildUrl(path, queryBuilder)
        httpClient.post(url) {
            headers(headers)
            body?.let { setBody(it) }
            contentType(ContentType.Application.Json)
        }
    }

    override suspend fun <T : Any> deleteWithClass(
        path: String,
        type: KClass<T>,
        body: Any?,
        queryBuilder: QueryParamsScope.() -> Unit,
        headers: HeadersBuilder.() -> Unit
    ): Result<T> = performRequest(type) {
        val url = buildUrl(path, queryBuilder)
        httpClient.delete(url) {
            headers(headers)
            body?.let { setBody(it) }
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
                    is String, is Number, is Boolean -> parameters.append(key, value.toString())
                    is List<*> -> value.filterNotNull()
                        .forEach { parameters.append(key, it.toString()) }

                    null -> Unit // Ignore null parameters
                    else -> parameters.append(key, value.toString()) // Default case
                }
            }
        }
        return urlBuilder.buildString()
    }

    private suspend fun <T : Any> performRequest(
        type: KClass<T>,
        block: suspend () -> HttpResponse
    ): Result<T> = withContext(Dispatchers.IO) {
        try {
            val response = block()
            if (response.status.isSuccess()) {
                Result.success(response.body(TypeInfo(type)))
            } else {
                Result.failure(Exception("HTTP error ${response.status}: ${response.bodyAsText()}"))
            }
        } catch (e: Exception) {
            ensureActive()
            Result.failure(e)
        }
    }

    override fun close() {
        httpClient.close()
    }

    companion object {
        private const val BASE_URL = "https://dolphin-app-zeoxd.ondigitalocean.app"
    }
}