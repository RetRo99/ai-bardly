package com.retro99.auth.data.remote

interface AuthRemoteDataSource {
    suspend fun generateBearerToken(id: String): Result<String>
}