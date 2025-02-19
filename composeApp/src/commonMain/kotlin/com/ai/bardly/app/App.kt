package com.ai.bardly.app

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import bardlyLightColors
import com.ai.bardly.feature.auth.ui.LoginScreen
import com.ai.bardly.feature.main.MainScreen
import com.ai.bardly.feature.onboarding.RootOnboardingScreen
import com.arkivanov.decompose.ExperimentalDecomposeApi
import com.arkivanov.decompose.extensions.compose.experimental.stack.ChildStack
import me.tatarka.inject.annotations.Inject

typealias App = @Composable () -> Unit

@OptIn(ExperimentalDecomposeApi::class)
@Inject
@Composable
fun App(
    applicationComponent: AppPresenter,
) {

    MaterialTheme(
        colorScheme = bardlyLightColors
    ) {
        ChildStack(
            stack = applicationComponent.childStack,
        ) {
            when (val child = it.instance) {
                is AppPresenter.Child.Main -> MainScreen(child.component)
                is AppPresenter.Child.Onboarding -> RootOnboardingScreen(child.component)
                is AppPresenter.Child.Auth -> LoginScreen(child.component)
            }
        }
    }
}