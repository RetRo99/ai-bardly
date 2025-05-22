package com.retro99.base.ui

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.value.MutableValue
import com.arkivanov.decompose.value.Value
import com.arkivanov.decompose.value.update
import com.arkivanov.essenty.lifecycle.Lifecycle
import com.arkivanov.essenty.lifecycle.coroutines.coroutineScope
import com.github.michaelbull.result.fold
import com.github.michaelbull.result.onFailure
import com.github.michaelbull.result.onSuccess
import com.retro99.base.result.AppError
import com.retro99.base.result.AppResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

interface BasePresenter<ScreenViewState, Intent : BaseScreenIntent> {
    val viewState: Value<BaseViewState<ScreenViewState>>
    fun onScreenIntent(intent: Intent)
    fun currentViewState(): ScreenViewState
}

abstract class BasePresenterImpl<ScreenViewState, Intent : BaseScreenIntent>(componentContext: ComponentContext) :
    BasePresenter<ScreenViewState, Intent>, ComponentContext by componentContext,
    Lifecycle.Callbacks {
    protected val scope = coroutineScope(Dispatchers.Main + SupervisorJob())

    protected open val initialState: BaseViewState<ScreenViewState> = BaseViewState.Loading
    protected abstract val defaultViewState: ScreenViewState

    private val _viewState by lazy { MutableValue(initialState) }
    override val viewState: Value<BaseViewState<ScreenViewState>> by lazy { _viewState }

    init {
        lifecycle.subscribe(this)
    }

    final override fun onScreenIntent(intent: Intent) {
        handleScreenIntent(intent)
    }

    final override fun currentViewState(): ScreenViewState {
        return (viewState.value as? BaseViewState.Success<ScreenViewState>)?.data
            ?: defaultViewState
    }

    abstract fun handleScreenIntent(intent: Intent)

    @Suppress("UNCHECKED_CAST")
    protected fun updateOrSetSuccess(update: (ScreenViewState) -> ScreenViewState) {
        _viewState.update { currentState ->
            when (currentState) {
                is BaseViewState.Success<*> -> BaseViewState.Success(update(currentState.data as ScreenViewState))
                else -> BaseViewState.Success(update(defaultViewState))
            }
        }
    }

    protected fun setError(
        error: AppError,
    ) {
        _viewState.update {
            BaseViewState.Error(error)
        }
    }

    protected fun <T> launchDataOperation(
        block: suspend () -> AppResult<T>,
        onError: (AppError) -> Unit = ::setError,
        onSuccess: (T) -> Unit,
    ): Job {
        return scope.launch {
            block()
                .onSuccess { data ->
                    onSuccess(data)
                }
                .onFailure { error ->
                    onError(error)
                }
        }
    }

    protected fun <T> launchOperation(
        block: suspend () -> Flow<AppResult<T>>,
        onError: (AppError) -> Unit = ::setError,
        onSuccess: (T) -> Unit,
    ): Job {
        return scope.launch {
            val flow = block()
            flow.collect { result ->
                result
                    .onSuccess { data ->
                        onSuccess(data)
                    }
                    .onFailure { error ->
                        onError(error)
                    }
            }
        }
    }

    protected fun setLoading() {
        _viewState.update {
            BaseViewState.Loading
        }
    }
}
