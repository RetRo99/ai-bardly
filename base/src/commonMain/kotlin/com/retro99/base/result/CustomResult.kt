package com.retro99.base.result

import com.github.michaelbull.result.Result

typealias AppResult<T> = Result<T, AppError>

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