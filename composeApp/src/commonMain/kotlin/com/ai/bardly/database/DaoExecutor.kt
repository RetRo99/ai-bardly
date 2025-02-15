package com.ai.bardly.database

import com.ai.bardly.analytics.Analytics
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.ensureActive
import kotlinx.coroutines.withContext
import me.tatarka.inject.annotations.Inject
import software.amazon.lastmile.kotlin.inject.anvil.AppScope
import software.amazon.lastmile.kotlin.inject.anvil.SingleIn

@Inject
@SingleIn(AppScope::class)
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