package com.retro99.data

import com.retro99.auth.domain.UserRepository
import com.retro99.auth.domain.model.UserDomainModel
import com.retro99.data.remote.UsersRemoteDataSource
import com.retro99.data.remote.model.toDomainModel
import me.tatarka.inject.annotations.Inject
import software.amazon.lastmile.kotlin.inject.anvil.AppScope
import software.amazon.lastmile.kotlin.inject.anvil.ContributesBinding
import software.amazon.lastmile.kotlin.inject.anvil.SingleIn

@Inject
@SingleIn(AppScope::class)
@ContributesBinding(AppScope::class)
class UserDataRepository(
    private val remoteSource: UsersRemoteDataSource,
) : UserRepository {

    override suspend fun getCurrentUser(): UserDomainModel? {
        return remoteSource.getCurrentUser()?.toDomainModel()
    }

    override suspend fun createUserWithEmailAndPassword(
        email: String,
        password: String
    ): Result<UserDomainModel?> {
        return remoteSource.createUserWithEmailAndPassword(email, password)
            .map { it?.toDomainModel() }
    }

    override suspend fun fetchUserWithEmailAndPassword(
        email: String,
        password: String
    ): Result<UserDomainModel?> {
        return remoteSource.fetchUserWithEmailAndPassword(email, password)
            .map { it?.toDomainModel() }
    }
}
