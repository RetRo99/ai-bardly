package com.ai.bardly.navigation.root.application

import com.ai.bardly.feature.main.MainPresenter
import com.ai.bardly.feature.onboarding.OnboardingPresenter
import com.ai.bardly.navigation.root.RootDecomposeComponent
import kotlinx.serialization.Serializable

interface RootPresenter :
    RootDecomposeComponent<RootPresenter.RootChild, RootPresenter.RootConfig> {

    sealed interface RootChild {
        data class Main(val component: MainPresenter) : RootChild
        data class Onboarding(val component: OnboardingPresenter) : RootChild
    }

    @Serializable
    sealed class RootConfig {
        @Serializable
        data object Main : RootConfig()

        @Serializable
        data object Onboarding : RootConfig()
    }
}