package com.ai.bardly.feature.auth

import com.ai.bardly.decompose.RootDecomposeComponent
import com.ai.bardly.feature.auth.ui.signin.LoginPresenter
import kotlinx.serialization.Serializable

interface AuthPresenter :
    RootDecomposeComponent<AuthPresenter.Child, AuthPresenter.Config> {

    fun navigate(config: Config)

    sealed interface Child {
        data class SignIn(val component: LoginPresenter) : Child
    }

    @Serializable
    sealed interface Config {
        @Serializable
        data object SignIn : Config
    }
}