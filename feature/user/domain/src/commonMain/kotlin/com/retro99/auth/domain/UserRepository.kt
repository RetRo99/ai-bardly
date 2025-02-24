package com.retro99.auth.domain

import com.retro99.auth.domain.model.UserDomainModel

interface UserRepository {
    suspend fun getCurrentUser(): UserDomainModel?

    suspend fun createUserWithEmailAndPassword(
        email: String,
        password: String,
    ): Result<UserDomainModel?>

    suspend fun fetchUserWithEmailAndPassword(
        email: String,
        password: String,
    ): Result<UserDomainModel?>
}