package com.ai.bardly.feature.home.ui.root

import com.ai.bardly.base.BaseComponent
import com.ai.bardly.feature.games.ui.details.GameDetailsComponent
import com.ai.bardly.feature.games.ui.model.GameUiModel
import com.ai.bardly.feature.home.ui.HomeComponent
import com.ai.bardly.navigation.root.RootComponent
import kotlinx.serialization.Serializable

interface RootHomeComponent : BaseComponent<RootHomeViewState, RootHomeIntent>,
    RootComponent<RootHomeComponent.HomeChild> {
    sealed interface HomeChild {
        data class Home(val component: HomeComponent) : HomeChild
        data class GameDetails(val component: GameDetailsComponent) : HomeChild
    }

    @Serializable
    sealed interface HomeConfig {
        @Serializable
        data object Home : HomeConfig

        @Serializable
        data class GameDetails(val game: GameUiModel) : HomeConfig
    }
}
