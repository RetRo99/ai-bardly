package com.ai.bardly.feature.onboarding

import com.ai.bardly.decompose.RootDecomposeComponent
import com.ai.bardly.feature.onboarding.welcome.ui.WelcomePresenter
import kotlinx.serialization.Serializable

interface OnboardingPresenter :
    RootDecomposeComponent<OnboardingPresenter.OnboardingChild, OnboardingPresenter.OnboardingConfig> {

    fun navigate(config: OnboardingConfig)

    sealed interface OnboardingChild {
        data class Welcome(val component: WelcomePresenter) : OnboardingChild
    }

    @Serializable
    sealed interface OnboardingConfig {
        @Serializable
        data object Welcome : OnboardingConfig
    }
}