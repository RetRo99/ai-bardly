package com.retro99.auth.data.remote

import com.retro99.base.result.AppResult

interface AuthRemoteDataSource {
    suspend fun generateBearerToken(id: String): AppResult<String>
}