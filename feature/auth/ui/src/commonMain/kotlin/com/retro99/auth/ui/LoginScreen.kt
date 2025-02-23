package com.retro99.auth.ui

import androidx.compose.runtime.Composable
import com.ai.bardly.decompose.RootChildStack
import com.arkivanov.decompose.ExperimentalDecomposeApi
import com.arkivanov.decompose.extensions.compose.experimental.stack.animation.slide
import com.arkivanov.decompose.extensions.compose.experimental.stack.animation.stackAnimation
import com.retro99.auth.ui.login.LoginScreen

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