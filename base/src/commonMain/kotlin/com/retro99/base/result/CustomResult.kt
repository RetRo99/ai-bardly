package com.retro99.base.result

import com.github.michaelbull.result.Result

typealias CustomResult<T, E> = Result<T, E>

typealias ThrowableResult<T> = Result<T, Throwable>
