package com.retro99.base

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.value.MutableValue
import com.arkivanov.decompose.value.Value
import com.arkivanov.decompose.value.update
import com.arkivanov.essenty.lifecycle.Lifecycle
import com.arkivanov.essenty.lifecycle.coroutines.coroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob

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
        throwable: Throwable,
    ) {
        _viewState.update {
            BaseViewState.Error(throwable)
        }
    }

    protected fun setLoading() {
        _viewState.update {
            BaseViewState.Loading
        }
    }
}