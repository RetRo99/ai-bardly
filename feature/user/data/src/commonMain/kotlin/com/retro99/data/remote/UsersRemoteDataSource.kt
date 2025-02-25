package com.retro99.data.remote

import com.retro99.data.remote.model.UserDto
import kotlinx.coroutines.flow.Flow

interface UsersRemoteDataSource {
    suspend fun getCurrentUser(): UserDto?
    suspend fun createUserWithEmailAndPassword(email: String, password: String): Result<UserDto?>
    suspend fun fetchUserWithEmailAndPassword(email: String, password: String): Result<UserDto?>
    suspend fun getCurrentUserFirebaseToken(): String?
    fun observeCurrentUser(): Flow<UserDto?>
}