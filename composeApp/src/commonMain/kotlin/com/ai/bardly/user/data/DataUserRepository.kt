package com.ai.bardly.user.data

import com.ai.bardly.user.data.remote.UsersRemoteDataSource
import com.ai.bardly.user.domain.UserRepository
import com.ai.bardly.user.domain.model.UserDomainModel
import com.ai.bardly.user.domain.model.toDomainModel
import me.tatarka.inject.annotations.Inject
import software.amazon.lastmile.kotlin.inject.anvil.AppScope
import software.amazon.lastmile.kotlin.inject.anvil.ContributesBinding
import software.amazon.lastmile.kotlin.inject.anvil.SingleIn

@Inject
@SingleIn(AppScope::class)
@ContributesBinding(AppScope::class)
class DataUserRepository(
    private val remoteSource: UsersRemoteDataSource,
) : UserRepository {

    override suspend fun getCurrentUser(): UserDomainModel? {
        return remoteSource.getCurrentUser()?.toDomainModel()
    }

    override suspend fun createUserWithEmailAndPassword(
        email: String,
        password: String
    ): UserDomainModel? {
        return remoteSource.createUserWithEmailAndPassword(email, password)?.toDomainModel()
    }

    override suspend fun fetchUserWithEmailAndPassword(
        email: String,
        password: String
    ): UserDomainModel? {
        return remoteSource.fetchUserWithEmailAndPassword(email, password)?.toDomainModel()
    }
}
