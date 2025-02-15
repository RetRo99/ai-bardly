package com.ai.bardly.navigation.root.application

import com.ai.bardly.navigation.root.RootComponent
import com.ai.bardly.navigation.root.main.MainNavigationComponent
import kotlinx.serialization.Serializable

interface RootPresenter : RootComponent<RootPresenter.ApplicationChild, RootPresenter.RootConfig> {
    sealed interface ApplicationChild {
        data class Main(val component: MainNavigationComponent) : ApplicationChild
    }

    @Serializable
    sealed class RootConfig {
        @Serializable
        data object Main : RootConfig()
    }
}