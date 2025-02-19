package com.ai.bardly.user.data.remote

import com.ai.bardly.user.data.remote.model.UserDto

interface UsersRemoteDataSource {
    suspend fun getCurrentUser(): UserDto?
    suspend fun createUserWithEmailAndPassword(email: String, password: String): Result<UserDto?>
    suspend fun fetchUserWithEmailAndPassword(email: String, password: String): Result<UserDto?>
    suspend fun getCurrentUserFirebaseToken(): String?
}