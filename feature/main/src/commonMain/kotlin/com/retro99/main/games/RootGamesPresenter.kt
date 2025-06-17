package com.retro99.main.games

import com.bardly.games.ui.details.GameDetailsRootPresenter
import com.bardly.games.ui.list.GamesListComponent
import com.bardly.games.ui.model.GameUiModel
import com.retro99.base.ui.BasePresenter
import com.retro99.base.ui.decompose.RootDecomposeComponent
import kotlinx.serialization.Serializable

interface RootGamesPresenter : BasePresenter<RootGamesViewState, RootGamesIntent>,
    RootDecomposeComponent<RootGamesPresenter.Child, RootGamesPresenter.Config> {

    sealed interface Child {
        data class GamesList(val component: GamesListComponent) : Child
        data class RootGameDetails(val component: GameDetailsRootPresenter) : Child
    }

    @Serializable
    sealed interface Config {
        @Serializable
        data object GamesList : Config

        @Serializable
        data class RootGameDetails(val game: GameUiModel) : Config
    }
}
