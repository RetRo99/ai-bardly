package com.ai.bardly.feature.auth

import androidx.compose.runtime.Composable
import com.ai.bardly.decompose.RootChildStack
import com.ai.bardly.feature.auth.ui.login.LoginScreen
import com.arkivanov.decompose.ExperimentalDecomposeApi

@OptIn(ExperimentalDecomposeApi::class)
@Composable
fun LoginScreen(component: AuthPresenter) {
    RootChildStack(
        root = component
    ) {
        when (val child = it.instance) {
            is AuthPresenter.Child.SignIn -> LoginScreen(child.component)
        }
    }
}