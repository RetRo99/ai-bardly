package com.ai.bardly.feature.auth.data.tokens

import com.ai.bardly.feature.auth.data.remote.AuthRemoteDataSource
import com.ai.bardly.user.data.remote.UsersRemoteDataSource
import me.tatarka.inject.annotations.Inject
import retro99.games.api.tokens.BearerTokenRefresher
import software.amazon.lastmile.kotlin.inject.anvil.AppScope
import software.amazon.lastmile.kotlin.inject.anvil.ContributesBinding
import software.amazon.lastmile.kotlin.inject.anvil.SingleIn

@Inject
@SingleIn(AppScope::class)
@ContributesBinding(AppScope::class)
class DefaultTokenRefresher(
    // Lazy inject because of circular dependency
    val authDataSourceProvider: () -> AuthRemoteDataSource,
    val userDataSource: UsersRemoteDataSource,
) : BearerTokenRefresher {

    override suspend fun refreshBearerToken(): String? {
        val firebaseToken = userDataSource.getCurrentUserFirebaseToken()
        return firebaseToken?.let { authDataSourceProvider().generateBearerToken(it) }?.getOrNull()
    }
}
