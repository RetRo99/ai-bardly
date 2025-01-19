package com.ai.bardly.base

import androidx.lifecycle.ViewModel
import com.ai.bardly.analytics.Analytics
import com.ai.bardly.navigation.GeneralDestination
import com.ai.bardly.navigation.NavigationManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

abstract class BaseViewModel<ScreenData> : ViewModel(), KoinComponent {
    protected open val initialState: BaseViewState<ScreenData> = BaseViewState.Loading
    protected abstract val defaultScreenData: ScreenData

    private val _viewState by lazy { MutableStateFlow(initialState) }
    val viewState: StateFlow<BaseViewState<ScreenData>> by lazy { _viewState.asStateFlow() }

    protected val analytics by inject<Analytics>()

    private val navigationManager by inject<NavigationManager>()

    protected fun updateOrSetSuccess(update: (ScreenData) -> ScreenData) {
        _viewState.update { currentState ->
            when (currentState) {
                is BaseViewState.Success<*> -> BaseViewState.Success(update(currentState.data as ScreenData))
                else -> BaseViewState.Success(update(defaultScreenData))
            }
        }
    }

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