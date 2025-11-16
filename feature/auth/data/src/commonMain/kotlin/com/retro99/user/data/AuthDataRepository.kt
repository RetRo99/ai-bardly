package com.retro99.user.data

import com.retro99.base.result.AppResult
import com.retro99.user.data.remote.AuthRemoteDataSource
import com.retro99.user.domain.AuthRepository
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
    override suspend fun generateBearerToken(id: String): AppResult<String> {
        return remoteSource.generateBearerToken(id)
    }
}
