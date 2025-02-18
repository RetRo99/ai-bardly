package com.ai.bardly.feature.login

import com.ai.bardly.feature.login.ui.SignInPresenter
import com.ai.bardly.navigation.root.RootDecomposeComponent
import kotlinx.serialization.Serializable

interface LoginPresenter :
    RootDecomposeComponent<LoginPresenter.Child, LoginPresenter.Config> {

    fun navigate(config: Config)

    sealed interface Child {
        data class SignIn(val component: SignInPresenter) : Child
    }

    @Serializable
    sealed interface Config {
        @Serializable
        data object SignIn : Config
    }
}