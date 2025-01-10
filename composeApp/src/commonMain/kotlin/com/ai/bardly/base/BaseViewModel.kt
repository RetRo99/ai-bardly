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

abstract class BaseViewModel<VIEW_STATE : BaseViewState<*>> : ViewModel(), KoinComponent {

    protected open val initialState: VIEW_STATE
        get() = BaseViewState.Loading as VIEW_STATE // Default to Loading, allows override

    private val _viewState: MutableStateFlow<VIEW_STATE> by lazy { MutableStateFlow(initialState) }

    val viewState: StateFlow<VIEW_STATE> by lazy { _viewState.asStateFlow() }

    protected fun getCurrentState(): VIEW_STATE = _viewState.value

    protected val analytics by inject<Analytics>()

    private val navigationManager by inject<NavigationManager>()

    protected fun updateState(updatedState: (lastState: VIEW_STATE) -> VIEW_STATE) {
        _viewState.update {
            updatedState(it)
        }
    }

    protected fun navigateTo(destination: GeneralDestination) {
        navigationManager.navigate(destination)
    }

    protected fun navigateBack() {
        navigationManager.navigate(GeneralDestination.Back)
    }
}