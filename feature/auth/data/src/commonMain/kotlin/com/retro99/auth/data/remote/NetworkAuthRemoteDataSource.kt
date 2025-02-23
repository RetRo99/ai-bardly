package com.retro99.auth.data.remote

import io.ktor.http.HttpHeaders
import me.tatarka.inject.annotations.Inject
import retro99.games.api.NetworkClient
import retro99.games.api.post
import software.amazon.lastmile.kotlin.inject.anvil.AppScope
import software.amazon.lastmile.kotlin.inject.anvil.ContributesBinding
import software.amazon.lastmile.kotlin.inject.anvil.SingleIn

@Inject
@SingleIn(AppScope::class)
@ContributesBinding(AppScope::class)
class NetworkAuthRemoteDataSource(
    private val networkClient: NetworkClient
) : AuthRemoteDataSource {

    override suspend fun generateBearerToken(id: String): Result<String> {
        return networkClient.post<String>(
            path = "auth/login/firebase",
            headers = {
                append(HttpHeaders.Authorization, "Bearer $id")
            }
        )
    }
}
