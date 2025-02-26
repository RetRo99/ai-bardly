package com.retro99.base.result

import com.github.michaelbull.result.Result

typealias CustomResult<T, E> = Result<T, E>

typealias ThrowableResult<T> = Result<T, Throwable>

inline fun <V, E> Result<V, E>.andThenAlways(action: (Result<V, E>) -> Result<V, E>): Result<V, E> {
    return action(this)
}