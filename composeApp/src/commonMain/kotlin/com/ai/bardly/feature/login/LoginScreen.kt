package com.ai.bardly.feature.login

import androidx.compose.runtime.Composable
import com.ai.bardly.navigation.RootChildStack
import com.arkivanov.decompose.ExperimentalDecomposeApi

@OptIn(ExperimentalDecomposeApi::class)
@Composable
fun LoginScreen(component: LoginPresenter) {
    RootChildStack(
        root = component
    ) {
        when (it.instance) {
            LoginPresenter.Child.SignIn -> {
                // sign in
            }
        }
    }
}