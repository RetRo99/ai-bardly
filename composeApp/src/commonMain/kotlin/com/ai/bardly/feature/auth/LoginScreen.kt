package com.ai.bardly.feature.auth

import androidx.compose.runtime.Composable
import com.ai.bardly.decompose.RootChildStack
import com.ai.bardly.feature.auth.ui.login.LoginScreen
import com.arkivanov.decompose.ExperimentalDecomposeApi
import com.arkivanov.decompose.extensions.compose.experimental.stack.animation.slide
import com.arkivanov.decompose.extensions.compose.experimental.stack.animation.stackAnimation

@OptIn(ExperimentalDecomposeApi::class)
@Composable
fun LoginScreen(component: AuthPresenter) {
    RootChildStack(
        root = component,
        animation = stackAnimation(
            animator = slide(),
        ),
    ) {
        when (val child = it.instance) {
            is AuthPresenter.Child.SignIn -> LoginScreen(child.component)
            is AuthPresenter.Child.SignUp -> LoginScreen(child.component)
        }
    }
}