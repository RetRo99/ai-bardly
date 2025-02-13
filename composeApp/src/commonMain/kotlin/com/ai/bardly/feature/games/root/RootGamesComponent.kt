package com.ai.bardly.feature.games.root

import com.ai.bardly.base.BaseComponent
import com.ai.bardly.feature.games.ui.details.GameDetailsComponent
import com.ai.bardly.feature.games.ui.list.GamesListComponent
import com.ai.bardly.feature.games.ui.model.GameUiModel
import com.ai.bardly.navigation.root.RootComponent
import kotlinx.serialization.Serializable

interface RootGamesComponent : BaseComponent<RootGamesViewState, RootGamesIntent>,
    RootComponent<RootGamesComponent.GamesChild> {

    sealed interface GamesChild {
        data class GamesList(val component: GamesListComponent) : GamesChild
        data class GameDetails(val component: GameDetailsComponent) : GamesChild
    }

    @Serializable
    sealed interface GamesConfig {
        @Serializable
        data object GamesList : GamesConfig

        @Serializable
        data class GameDetails(val game: GameUiModel) : GamesConfig
    }
}
