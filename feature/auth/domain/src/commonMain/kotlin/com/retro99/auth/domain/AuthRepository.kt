package com.retro99.auth.domain

interface AuthRepository {
    suspend fun generateBearerToken(id: String): Result<String>
}