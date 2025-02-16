package com.ai.bardly.feature.onboarding

import com.ai.bardly.feature.main.chats.root.RootRecentPresenter
import com.ai.bardly.navigation.root.RootDecomposeComponent
import kotlinx.serialization.Serializable

interface OnboardingPresenter :
    RootDecomposeComponent<OnboardingPresenter.OnboardingChild, OnboardingPresenter.OnboardingConfig> {

    fun navigate(config: OnboardingConfig)

    sealed interface OnboardingChild {
        data class Welcome(val component: RootRecentPresenter) : OnboardingChild
    }

    @Serializable
    sealed interface OnboardingConfig {
        @Serializable
        data object Welcome : OnboardingConfig
    }
}