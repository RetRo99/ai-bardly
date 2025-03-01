package com.ai.bardly.app

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import bardlyLightColors
import com.ai.bardly.feature.onboarding.RootOnboardingScreen
import com.arkivanov.decompose.ExperimentalDecomposeApi
import com.arkivanov.decompose.extensions.compose.experimental.stack.ChildStack
import com.arkivanov.decompose.extensions.compose.experimental.stack.animation.slide
import com.arkivanov.decompose.extensions.compose.experimental.stack.animation.stackAnimation
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.value.Value
import com.retro99.auth.ui.AuthScreen
import com.retro99.base.ui.BaseScreen
import com.retro99.base.ui.IntentDispatcher
import com.retro99.main.MainScreen
import com.retro99.snackbar.api.SnackbarHost
import me.tatarka.inject.annotations.Inject

typealias App = @Composable () -> Unit

@Inject
@Composable
fun App(
    appPresenter: AppPresenter,
) {

    BaseScreen(appPresenter) { viewState, intentDispatcher ->
        AppScreenContent(
            viewState = viewState,
            intentDispatcher = intentDispatcher,
            childStack = appPresenter.childStack,
        )
    }
}

@OptIn(ExperimentalDecomposeApi::class)
@Composable
private fun AppScreenContent(
    viewState: AppViewState,
    intentDispatcher: IntentDispatcher<AppScreenIntent>,
    childStack: Value<ChildStack<AppPresenter.Config, AppPresenter.Child>>,
) {

    MaterialTheme(
        colorScheme = bardlyLightColors
    ) {
        SnackbarHost(
            message = viewState.snackbarData,
            hideSnackBar = { intentDispatcher(AppScreenIntent.SnackbarMessageShown) }
        ) {
            ChildStack(
                stack = childStack,
                animation = stackAnimation(
                    animator = slide(),
                )
            ) {
                when (val child = it.instance) {
                    is AppPresenter.Child.Main -> MainScreen(child.component)
                    is AppPresenter.Child.Onboarding -> RootOnboardingScreen(child.component)
                    is AppPresenter.Child.Auth -> AuthScreen(child.component)
                }
            }

        }
    }
}