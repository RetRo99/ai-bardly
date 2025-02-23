package com.retro99.database.implementation

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.ensureActive
import kotlinx.coroutines.withContext
import me.tatarka.inject.annotations.Inject
import retro99.analytics.api.Analytics
import software.amazon.lastmile.kotlin.inject.anvil.AppScope
import software.amazon.lastmile.kotlin.inject.anvil.ContributesBinding
import software.amazon.lastmile.kotlin.inject.anvil.SingleIn

@Inject
@SingleIn(AppScope::class)
@ContributesBinding(AppScope::class)
class DaoExecutor(
    val analytics: Analytics,
) : DatabaseExecutor {
    override suspend fun <T> executeDatabaseOperation(operation: suspend () -> T): Result<T> =
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