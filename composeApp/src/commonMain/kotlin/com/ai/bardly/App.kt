package com.ai.bardly

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import bardlyLightColors
import com.ai.bardly.feature.main.MainScreen
import com.ai.bardly.feature.onboarding.RootOnboardingScreen
import com.ai.bardly.navigation.root.application.RootPresenter
import com.arkivanov.decompose.ExperimentalDecomposeApi
import com.arkivanov.decompose.extensions.compose.experimental.stack.ChildStack
import me.tatarka.inject.annotations.Inject

typealias App = @Composable () -> Unit

@OptIn(ExperimentalDecomposeApi::class)
@Inject
@Composable
fun App(
    applicationComponent: RootPresenter,
) {

    MaterialTheme(
        colorScheme = bardlyLightColors
    ) {
        ChildStack(
            stack = applicationComponent.childStack,
        ) {
            when (val child = it.instance) {
                is RootPresenter.RootChild.Main -> MainScreen(child.component)
                is RootPresenter.RootChild.Onboarding -> RootOnboardingScreen(child.component)
            }
        }
    }
}