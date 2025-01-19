package com.ai.bardly.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.ai.bardly.base.BaseViewModel
import com.ai.bardly.base.BaseViewState
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.parameter.parametersOf

@Composable
inline fun <reified ViewModel : BaseViewModel<ScreenData>, ScreenData> BaseScreen(
    noinline loadingContent: @Composable () -> Unit = { LoadingScreen() },
    noinline errorContent: @Composable (BaseViewState.Error) -> Unit = { ErrorScreen(it) },
    vararg parameters: Any,
    noinline content: @Composable (ViewModel, ScreenData) -> Unit
) {
    val viewModel: ViewModel = koinViewModel { parametersOf(*parameters) }
    val viewState by viewModel.viewState.collectAsState()

    when (val state = viewState) {
        is BaseViewState.Success -> content(viewModel, state.data)
        is BaseViewState.Error -> errorContent(state)
        is BaseViewState.Loading -> loadingContent()
        else -> Unit
    }
}

@Composable
fun ErrorScreen(error: BaseViewState.Error) {
    Box(Modifier.fillMaxSize()) {
        Text("Error")
    }
}

@Composable
fun LoadingScreen() {
    Box(Modifier.fillMaxSize()) {
        Text("Loading")
    }
}