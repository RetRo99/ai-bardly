package com.ai.bardly.feature.auth.domain

interface AuthRepository {
    suspend fun generateBearerToken(id: String): Result<String>
}