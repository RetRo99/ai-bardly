package com.ai.bardly.feature.auth.data

import com.ai.bardly.feature.auth.data.remote.AuthRemoteDataSource
import com.ai.bardly.feature.auth.domain.AuthRepository
import me.tatarka.inject.annotations.Inject
import software.amazon.lastmile.kotlin.inject.anvil.AppScope
import software.amazon.lastmile.kotlin.inject.anvil.ContributesBinding
import software.amazon.lastmile.kotlin.inject.anvil.SingleIn

@Inject
@SingleIn(AppScope::class)
@ContributesBinding(AppScope::class)
class AuthDataRepository(
    private val remoteSource: AuthRemoteDataSource,
) : AuthRepository {
    override suspend fun generateBearerToken(id: String): Result<String> {
        return remoteSource.generateBearerToken(id)
    }
}
