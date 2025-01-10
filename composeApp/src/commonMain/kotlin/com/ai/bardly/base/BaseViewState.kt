package com.ai.bardly.base

sealed class BaseViewState<out T> {
    data object Loading : BaseViewState<Nothing>()

    data class Loaded<T>(
        val data: T
    ) : BaseViewState<T>()

    data class Error(
        val message: String,
        val code: String? = null,
        val throwable: Throwable? = null
    ) : BaseViewState<Nothing>()

    fun isLoading() = this is Loading
    fun isLoaded() = this is Loaded<*>
    fun isError() = this is Error

}