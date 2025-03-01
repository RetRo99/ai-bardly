package com.retro99.auth.ui

import androidx.compose.runtime.Composable
import com.arkivanov.decompose.ExperimentalDecomposeApi
import com.arkivanov.decompose.extensions.compose.experimental.stack.animation.slide
import com.arkivanov.decompose.extensions.compose.experimental.stack.animation.stackAnimation
import com.retro99.auth.ui.login.LoginScreen
import com.retro99.base.ui.decompose.RootChildStack

@OptIn(ExperimentalDecomposeApi::class)
@Composable
fun AuthScreen(component: AuthPresenter) {
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