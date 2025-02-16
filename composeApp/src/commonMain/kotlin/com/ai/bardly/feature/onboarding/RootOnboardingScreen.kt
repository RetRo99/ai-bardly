package com.ai.bardly.feature.onboarding

import androidx.compose.runtime.Composable
import com.ai.bardly.feature.onboarding.welcome.ui.WelcomeScreen
import com.arkivanov.decompose.ExperimentalDecomposeApi
import com.arkivanov.decompose.extensions.compose.experimental.stack.ChildStack
import com.arkivanov.decompose.extensions.compose.experimental.stack.animation.fade
import com.arkivanov.decompose.extensions.compose.experimental.stack.animation.stackAnimation

@OptIn(ExperimentalDecomposeApi::class)
@Composable
fun RootOnboardingScreen(component: OnboardingPresenter) {
    ChildStack(
        stack = component.childStack,
        animation = stackAnimation(fade()),
    ) {
        when (val child = it.instance) {
            is OnboardingPresenter.OnboardingChild.Welcome -> WelcomeScreen(child.component)
        }
    }
}