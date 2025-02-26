package com.retro99.auth.domain

import com.retro99.auth.domain.model.UserDomainModel
import com.retro99.base.result.AppResult
import kotlinx.coroutines.flow.Flow

interface UserRepository {
    suspend fun getCurrentUser(): UserDomainModel?

    fun observeCurrentUser(): Flow<UserDomainModel?>

    suspend fun createUserWithEmailAndPassword(
        email: String,
        password: String,
    ): AppResult<UserDomainModel?>

    suspend fun fetchUserWithEmailAndPassword(
        email: String,
        password: String,
    ): AppResult<UserDomainModel?>
}