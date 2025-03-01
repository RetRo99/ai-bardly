package com.ai.bardly.app

import com.ai.bardly.feature.onboarding.OnboardingPresenter
import com.retro99.auth.ui.AuthPresenter
import com.retro99.base.ui.BasePresenter
import com.retro99.base.ui.decompose.RootDecomposeComponent
import com.retro99.main.MainPresenter
import kotlinx.serialization.Serializable

interface AppPresenter : BasePresenter<AppViewState, AppScreenIntent>,
    RootDecomposeComponent<AppPresenter.Child, AppPresenter.Config> {

    sealed interface Child {
        data class Main(val component: MainPresenter) : Child
        data class Onboarding(val component: OnboardingPresenter) : Child
        data class Auth(val component: AuthPresenter) : Child
    }

    @Serializable
    sealed class Config {
        @Serializable
        data object Main : Config()

        @Serializable
        data object Onboarding : Config()

        @Serializable
        data object Auth : Config()
    }
}