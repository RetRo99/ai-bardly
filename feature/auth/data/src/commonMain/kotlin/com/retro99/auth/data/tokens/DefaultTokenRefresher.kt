package com.retro99.auth.data.tokens

import com.github.michaelbull.result.get
import com.retro99.auth.data.remote.AuthRemoteDataSource
import com.retro99.data.remote.UsersRemoteDataSource
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
        return firebaseToken?.let { authDataSourceProvider().generateBearerToken(it) }?.get()
    }
}
