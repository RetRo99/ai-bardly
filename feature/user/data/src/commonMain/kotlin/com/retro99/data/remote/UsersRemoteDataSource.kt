package com.retro99.data.remote

import com.retro99.data.remote.model.UserDto

interface UsersRemoteDataSource {
    suspend fun getCurrentUser(): UserDto?
    suspend fun createUserWithEmailAndPassword(email: String, password: String): Result<UserDto?>
    suspend fun fetchUserWithEmailAndPassword(email: String, password: String): Result<UserDto?>
    suspend fun getCurrentUserFirebaseToken(): String?
}