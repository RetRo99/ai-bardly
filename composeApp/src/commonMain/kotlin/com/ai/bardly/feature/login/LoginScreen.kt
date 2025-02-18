package com.ai.bardly.feature.login

import androidx.compose.runtime.Composable
import com.ai.bardly.decompose.RootChildStack
import com.ai.bardly.feature.login.ui.SignInScreen
import com.arkivanov.decompose.ExperimentalDecomposeApi

@OptIn(ExperimentalDecomposeApi::class)
@Composable
fun LoginScreen(component: LoginPresenter) {
    RootChildStack(
        root = component
    ) {
        when (val child = it.instance) {
            is LoginPresenter.Child.SignIn -> SignInScreen(child.component)
        }
    }
}