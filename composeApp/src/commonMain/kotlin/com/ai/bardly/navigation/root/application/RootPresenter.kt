package com.ai.bardly.navigation.root.application

import com.ai.bardly.navigation.root.RootDecomposeComponent
import com.ai.bardly.navigation.root.main.MainPresenter
import kotlinx.serialization.Serializable

interface RootPresenter :
    RootDecomposeComponent<RootPresenter.ApplicationChild, RootPresenter.RootConfig> {
    sealed interface ApplicationChild {
        data class Main(val component: MainPresenter) : ApplicationChild
    }

    @Serializable
    sealed class RootConfig {
        @Serializable
        data object Main : RootConfig()
    }
}