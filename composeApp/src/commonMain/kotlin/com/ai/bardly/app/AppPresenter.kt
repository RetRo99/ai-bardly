package com.ai.bardly.app

import com.ai.bardly.decompose.RootDecomposeComponent
import com.ai.bardly.feature.auth.LoginPresenter
import com.ai.bardly.feature.main.MainPresenter
import com.ai.bardly.feature.onboarding.OnboardingPresenter
import kotlinx.serialization.Serializable

interface AppPresenter :
    RootDecomposeComponent<AppPresenter.Child, AppPresenter.Config> {

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