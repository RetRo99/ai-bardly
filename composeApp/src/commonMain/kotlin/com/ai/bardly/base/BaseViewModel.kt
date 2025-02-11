package com.ai.bardly.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ai.bardly.analytics.Analytics
import com.ai.bardly.navigation.GeneralDestination
import com.ai.bardly.navigation.NavigationManager
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.value.MutableValue
import com.arkivanov.decompose.value.Value
import com.arkivanov.decompose.value.update
import com.arkivanov.essenty.lifecycle.coroutines.coroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

abstract class BaseViewModel<ScreenViewState, Intent : ScreenIntent> : ViewModel(), KoinComponent {
    protected open val initialState: BaseViewState<ScreenViewState> = BaseViewState.Loading
    protected abstract val defaultViewState: ScreenViewState

    private val _viewState by lazy { MutableStateFlow(initialState) }
    val viewState: StateFlow<BaseViewState<ScreenViewState>> by lazy { _viewState.asStateFlow() }

    protected val analytics by inject<Analytics>()

    private val navigationManager by inject<NavigationManager>()

    fun onScreenIntent(intent: Intent) {
        viewModelScope.launch {
            handleScreenIntent(intent)
        }
    }

    fun currentViewState(): ScreenViewState {
        return (viewState.value as? BaseViewState.Success<ScreenViewState>)?.data
            ?: defaultViewState
    }

    open fun onScreenDisplayed() {}

    abstract suspend fun handleScreenIntent(intent: Intent)

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

    protected fun navigateTo(destination: GeneralDestination) {
        navigationManager.navigate(destination)
    }

    protected fun navigateBack() {
        navigationManager.navigate(GeneralDestination.Back)
    }
}

interface BaseComponent<ScreenViewState, Intent : ScreenIntent> {
    val viewState: Value<BaseViewState<ScreenViewState>>
    fun onScreenIntent(intent: Intent)
    fun currentViewState(): ScreenViewState
}

abstract class BaseComponentImpl<ScreenViewState, Intent : ScreenIntent>(componentContext: ComponentContext) :
    KoinComponent, BaseComponent<ScreenViewState, Intent>, ComponentContext by componentContext {
    protected val scope = coroutineScope(Dispatchers.Main + SupervisorJob())

    protected open val initialState: BaseViewState<ScreenViewState> = BaseViewState.Loading
    protected abstract val defaultViewState: ScreenViewState

    private val _viewState by lazy { MutableValue(initialState) }
    override val viewState: Value<BaseViewState<ScreenViewState>> by lazy { _viewState }

    protected val analytics by inject<Analytics>()

    private val navigationManager by inject<NavigationManager>()

    final override fun onScreenIntent(intent: Intent) {
        scope.launch {
            handleScreenIntent(intent)
        }
    }

    final override fun currentViewState(): ScreenViewState {
        return (viewState.value as? BaseViewState.Success<ScreenViewState>)?.data
            ?: defaultViewState
    }

    abstract suspend fun handleScreenIntent(intent: Intent)

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

    protected fun navigateTo(destination: GeneralDestination) {
        navigationManager.navigate(destination)
    }

    protected fun navigateBack() {
        navigationManager.navigate(GeneralDestination.Back)
    }
}