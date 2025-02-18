package com.ai.bardly.feature.login

import com.ai.bardly.navigation.root.RootDecomposeComponent
import kotlinx.serialization.Serializable

interface LoginPresenter :
    RootDecomposeComponent<LoginPresenter.Child, LoginPresenter.Config> {

    fun navigate(config: Config)

    sealed interface Child {
        data object SignIn : Child
    }

    @Serializable
    sealed interface Config {
        @Serializable
        data object SignIn : Config
    }
}