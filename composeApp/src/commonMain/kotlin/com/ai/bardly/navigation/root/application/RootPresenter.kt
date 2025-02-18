package com.ai.bardly.navigation.root.application

import com.ai.bardly.feature.login.LoginPresenter
import com.ai.bardly.feature.main.MainPresenter
import com.ai.bardly.feature.onboarding.OnboardingPresenter
import com.ai.bardly.navigation.root.RootDecomposeComponent
import kotlinx.serialization.Serializable

interface RootPresenter :
    RootDecomposeComponent<RootPresenter.Child, RootPresenter.Config> {

    sealed interface Child {
        data class Main(val component: MainPresenter) : Child
        data class Onboarding(val component: OnboardingPresenter) : Child
        data class Login(val component: LoginPresenter) : Child
    }

    @Serializable
    sealed class Config {
        @Serializable
        data object Main : Config()

        @Serializable
        data object Onboarding : Config()

        @Serializable
        data object Login : Config()
    }
}