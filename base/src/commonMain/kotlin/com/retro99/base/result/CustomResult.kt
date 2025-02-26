package com.retro99.base.result

import com.github.michaelbull.result.Result

typealias AppResult<T> = Result<T, Error>

inline fun <V, E> Result<V, E>.andThenAlways(action: (Result<V, E>) -> Result<V, E>): Result<V, E> {
    return action(this)
}
sealed class Error {
    data class NetworkError(val throwable: Throwable, val isConnectivity: Boolean = false) : Error()
    data class ApiError(val code: Int, val message: String? = null) : Error()
    data class DatabaseError(val throwable: Throwable, val table: String? = null) : Error()
    data class UnknownError(val throwable: Throwable) : Error()
}