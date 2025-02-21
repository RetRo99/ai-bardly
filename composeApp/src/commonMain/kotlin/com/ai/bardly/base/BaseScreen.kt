package com.ai.bardly.base

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.arkivanov.decompose.extensions.compose.subscribeAsState

@Composable
fun <ScreenViewState, Intent : BaseScreenIntent> BaseScreen(
    component: BasePresenter<ScreenViewState, Intent>,
    loadingContent: @Composable () -> Unit = { LoadingScreen() },
    errorContent: @Composable (BaseViewState.Error) -> Unit = { ErrorScreen(it) },
    content: @Composable (ScreenViewState, IntentDispatcher<Intent>) -> Unit
) {
    val viewState by component.viewState.subscribeAsState()

    Box(Modifier.fillMaxSize().background(MaterialTheme.colorScheme.background)) {
        when (val state = viewState) {
            is BaseViewState.Success -> content(
                state.data,
                IntentDispatcher(component::onScreenIntent)
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
        CircularProgressIndicator(
            modifier = Modifier.align(Alignment.Center)
        )
    }
}