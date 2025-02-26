package com.retro99.base.result

import com.github.michaelbull.result.Err
import com.github.michaelbull.result.Ok
import com.github.michaelbull.result.Result
import kotlin.contracts.ExperimentalContracts
import kotlin.contracts.InvocationKind
import kotlin.contracts.contract

typealias AppResult<T> = Result<T, AppError>

typealias CompletableResult = Result<Unit, AppError>

inline fun <V, E> Result<V, E>.andThenAlways(action: (Result<V, E>) -> Result<V, E>): Result<V, E> {
    return action(this)
}

sealed class AppError {
    data class NetworkError(val throwable: Throwable, val isConnectivity: Boolean = false) :
        AppError()

    data class ApiError(val code: Int, val message: String? = null) : AppError()
    data class DatabaseError(val throwable: Throwable, val table: String? = null) : AppError()
    data class UnknownError(val throwable: Throwable) : AppError()
}

/**
 * Calls the specified function [block] with [this] value as its receiver and returns its
 * encapsulated result if invocation was successful, catching any [Throwable] exception that was
 * thrown from the [block] function execution and encapsulating it as an AppError.
 */
@OptIn(ExperimentalContracts::class)
inline infix fun <T, V> T.runCatchingAsAppError(block: T.() -> V): Result<V, AppError> {
    contract {
        callsInPlace(block, InvocationKind.AT_MOST_ONCE)
    }
    return try {
        Ok(block())
    } catch (e: Throwable) {
        Err(AppError.UnknownError(e))
    }
}