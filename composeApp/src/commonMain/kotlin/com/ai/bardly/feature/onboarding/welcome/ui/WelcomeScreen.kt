package com.ai.bardly.feature.onboarding.welcome.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.ai.bardly.base.BaseScreen
import com.ai.bardly.base.IntentDispatcher

@Composable
fun WelcomeScreen(
    component: WelcomePresenter,
) {
    BaseScreen(component) { viewState, intentDispatcher ->
        HomeScreenContent(
            viewState = viewState,
            intentDispatcher = intentDispatcher,
        )
    }
}

@Composable
private fun HomeScreenContent(
    viewState: WelcomeViewState,
    intentDispatcher: IntentDispatcher<WelcomeIntent>,
) {
    Box(
        modifier = Modifier
            .fillMaxSize(),
    ) {
        Column {
            Text(
                text = "Welcome to bardly app",
            )
            Button(
                onClick = {
                    intentDispatcher(WelcomeIntent.OpenMain)
                },
            ) {
                Text(
                    text = "Navigate to main",
                )
            }
        }
    }
}