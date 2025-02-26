package com.retro99.auth.data.remote

import com.retro99.base.result.ThrowableResult

interface AuthRemoteDataSource {
    suspend fun generateBearerToken(id: String): ThrowableResult<String>
}