package com.ai.bardly.feature.auth.data.remote

interface AuthRemoteDataSource {
    suspend fun generateBearerToken(id: String): Result<String>
}