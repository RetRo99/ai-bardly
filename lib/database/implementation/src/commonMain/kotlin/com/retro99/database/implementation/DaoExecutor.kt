package com.retro99.database.implementation

import androidx.sqlite.SQLiteException
import com.github.michaelbull.result.Err
import com.github.michaelbull.result.Ok
import com.retro99.base.result.AppError
import com.retro99.base.result.AppResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.ensureActive
import kotlinx.coroutines.withContext
import me.tatarka.inject.annotations.Inject
import retro99.analytics.api.Analytics
import software.amazon.lastmile.kotlin.inject.anvil.AppScope
import software.amazon.lastmile.kotlin.inject.anvil.ContributesBinding
import software.amazon.lastmile.kotlin.inject.anvil.SingleIn
import kotlin.coroutines.cancellation.CancellationException

@Inject
@SingleIn(AppScope::class)
@ContributesBinding(AppScope::class)
class DaoExecutor(
    private val analytics: Analytics,
) : DatabaseExecutor {

    override suspend fun <T> executeDatabaseOperation(operation: suspend () -> T): AppResult<T> =
        withContext(Dispatchers.IO) {
            try {
                Ok(operation())
            } catch (e: Exception) {
                ensureActive() // Check if coroutine is still active

                // Log the exception
                analytics.logException(e, "Error executing dao operation")

                when (e) {
                    is CancellationException -> throw e // Re-throw cancellation exceptions

                    is SQLiteException -> {
                        // Extract table name from error message if possible
                        val tableName = extractTableName(e.message ?: "")
                        Err(
                            AppError.DatabaseError(
                                throwable = e,
                                table = tableName
                            )
                        )
                    }

                    else -> Err(AppError.UnknownError(throwable = e))
                }
            }
        }

    /**
     * Attempts to extract a table name from SQLite error messages.
     * This is a best-effort approach as error message formats can vary.
     */
    private fun extractTableName(errorMessage: String): String? {
        val tablePatterns = listOf(
            "table\\s+['`\"]?([\\w_]+)['`\"]?".toRegex(RegexOption.IGNORE_CASE),
            "constraint on\\s+['`\"]?([\\w_]+)['`\"]?".toRegex(RegexOption.IGNORE_CASE)
        )

        for (pattern in tablePatterns) {
            val match = pattern.find(errorMessage)
            if (match != null && match.groupValues.size > 1) {
                return match.groupValues[1]
            }
        }

        return null
    }
}