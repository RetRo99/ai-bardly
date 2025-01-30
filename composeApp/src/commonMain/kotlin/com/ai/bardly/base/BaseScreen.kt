package com.ai.bardly.base

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.parameter.parametersOf

@Composable
inline fun <reified ViewModel : BaseViewModel<ScreenViewState, Intent>, ScreenViewState, Intent : ScreenIntent> BaseScreen(
    noinline loadingContent: @Composable () -> Unit = { LoadingScreen() },
    noinline errorContent: @Composable (BaseViewState.Error) -> Unit = { ErrorScreen(it) },
    vararg parameters: Any = emptyArray(),
    noinline content: @Composable (ScreenViewState, IntentDispatcher<Intent>) -> Unit
) {
    val viewModel: ViewModel = koinViewModel { parametersOf(*parameters) }
    val viewState by viewModel.viewState.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.onScreenDisplayed()
    }
    Box(Modifier.fillMaxSize()) {
        when (val state = viewState) {
            is BaseViewState.Success -> content(
                state.data,
                IntentDispatcher(viewModel::onScreenIntent)
            )

            is BaseViewState.Error -> errorContent(state)
            is BaseViewState.Loading -> loadingContent()
        }
    }
}

@Composable
fun ErrorScreen(error: BaseViewState.Error) {
    Box(Modifier.fillMaxSize()) {
        Text(
            error.throwable.message ?: error.throwable.toString(), modifier = Modifier.align
                (
                Alignment
                    .Center
            )
        )
    }
}

@Composable
fun LoadingScreen() {
    Box(Modifier.fillMaxSize()) {
        Text("Loading", modifier = Modifier.align(Alignment.Center))
    }
}