package com.ai.bardly.navigation.root.application

import com.ai.bardly.navigation.root.RootComponent
import com.ai.bardly.navigation.root.main.MainComponent
import kotlinx.serialization.Serializable

interface DecomposeRoot : RootComponent<DecomposeRoot.ApplicationChild, DecomposeRoot.RootConfig> {
    sealed interface ApplicationChild {
        data class Main(val component: MainComponent) : ApplicationChild
    }

    @Serializable
    sealed class RootConfig {
        @Serializable
        data object Main : RootConfig()
    }
}