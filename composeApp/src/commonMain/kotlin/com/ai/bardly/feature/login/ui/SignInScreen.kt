package com.ai.bardly.feature.login.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.ai.bardly.base.BaseScreen
import com.ai.bardly.base.IntentDispatcher

@Composable
fun SignInScreen(
    component: SignInPresenter,
) {
    BaseScreen(component) { viewState, intentDispatcher ->
        SignInScreenContent(
            viewState = viewState,
            intentDispatcher = intentDispatcher,
        )
    }
}

@Composable
private fun SignInScreenContent(
    viewState: SignInViewState,
    intentDispatcher: IntentDispatcher<SignInIntent>,
) {
    Box(
        modifier = Modifier
            .fillMaxSize(),
    ) {
        Text(
            text = "sign in",
            style = MaterialTheme.typography.headlineLarge,
            modifier = Modifier.align(Alignment.Center)
        )
    }
}