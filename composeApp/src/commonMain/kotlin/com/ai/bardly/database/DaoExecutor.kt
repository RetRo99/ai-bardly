package com.ai.bardly.database

import com.ai.bardly.analytics.Analytics
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.ensureActive
import kotlinx.coroutines.withContext
import org.koin.kcore.annotation.Single

@Single
class DaoExecutor(
    val analytics: Analytics,
) {
    suspend fun <T> executeDaoOperation(operation: suspend () -> T): Result<T> =
        withContext(Dispatchers.IO) {
            try {
                Result.success(operation())
            } catch (e: Exception) {
                analytics.logException(e, "Error executing dao operation")
                ensureActive()
                Result.failure(e)
            }
        }
}