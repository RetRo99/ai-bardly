package com.retro99.base.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.arkivanov.decompose.extensions.compose.subscribeAsState
import com.retro99.base.result.AppError
import com.retro99.translations.StringRes
import org.jetbrains.compose.resources.stringResource
import resources.translations.error_api_generic
import resources.translations.error_api_unknown
import resources.translations.error_database_generic
import resources.translations.error_database_specific
import resources.translations.error_network_connectivity
import resources.translations.error_network_generic
import resources.translations.error_title
import resources.translations.error_unknown

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
        val errorMessage = stringResource(error.error.toStringRes())

        Column(
            modifier = Modifier
                .align(Alignment.Center)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(
                text = stringResource(StringRes.error_title),
                style = MaterialTheme.typography.headlineMedium,
                color = MaterialTheme.colorScheme.error,
                textAlign = TextAlign.Center
            )

            Text(
                text = errorMessage,
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.bodyLarge
            )

//            Button(onClick = {
//                 Implement retry logic here
//            }) {
//                Text(text = stringResource(StringRes.error_retry))
//            }
        }
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