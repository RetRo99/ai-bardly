package com.ai.bardly.feature.auth.ui

import com.ai.bardly.decompose.RootDecomposeComponent
import com.ai.bardly.feature.auth.ui.login.LoginPresenter
import kotlinx.serialization.Serializable

interface AuthPresenter :
    RootDecomposeComponent<AuthPresenter.Child, AuthPresenter.Config> {

    fun navigate(config: Config)

    sealed interface Child {
        data class SignIn(val component: LoginPresenter) : Child
        data class SignUp(val component: LoginPresenter) : Child
    }

    @Serializable
    sealed interface Config {
        @Serializable
        data object SignIn : Config

        @Serializable
        data object SignUp : Config
    }
}