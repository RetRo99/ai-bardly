package com.ai.bardly.base

sealed class BaseViewState<out T> {
    data object Loading : BaseViewState<Nothing>()

    data class Success<T>(
        val data: T
    ) : BaseViewState<T>()

    data class Error(
        val throwable: Throwable
    ) : BaseViewState<Nothing>()
}