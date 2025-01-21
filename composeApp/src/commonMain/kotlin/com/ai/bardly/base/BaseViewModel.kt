package com.ai.bardly.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ai.bardly.analytics.Analytics
import com.ai.bardly.navigation.GeneralDestination
import com.ai.bardly.navigation.NavigationManager
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

    protected fun updateOrSetSuccess(update: (ScreenViewState) -> ScreenViewState) {
        _viewState.update { currentState ->
            when (currentState) {
                is BaseViewState.Success<*> -> BaseViewState.Success(update(currentState.data as ScreenViewState))
                else -> BaseViewState.Success(update(defaultViewState))
            }
        }
    }

    fun onScreenIntent(intent: Intent) {
        viewModelScope.launch {
            handleScreenIntent(intent)
        }
    }

    abstract suspend fun handleScreenIntent(intent: Intent)

    protected fun setError(
        message: String? = null,
        code: String? = null,
        throwable: Throwable? = null
    ) {
        _viewState.update {
            BaseViewState.Error(message, code, throwable)
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