package com.retro99.user.ui

import com.retro99.base.ui.decompose.RootDecomposeComponent
import com.retro99.user.ui.login.LoginPresenter
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