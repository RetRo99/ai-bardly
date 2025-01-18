package com.ai.bardly.database

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.ensureActive
import kotlinx.coroutines.withContext

class DaoExecutor {
    suspend fun <T> executeDaoOperation(operation: suspend () -> T): Result<T> =
        withContext(Dispatchers.IO) {
            try {
                Result.success(operation())
            } catch (e: Exception) {
                ensureActive()
                Result.failure(e)
            }
        }
}