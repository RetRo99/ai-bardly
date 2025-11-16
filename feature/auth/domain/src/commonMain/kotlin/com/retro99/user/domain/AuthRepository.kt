package com.retro99.user.domain

import com.retro99.base.result.AppResult

interface AuthRepository {
    suspend fun generateBearerToken(id: String): AppResult<String>
}