package com.bardly.games.ui.details

import com.bardly.games.ui.model.GameUiModel
import com.retro99.base.ui.BasePresenter
import com.retro99.base.ui.decompose.RootDecomposeComponent
import kotlinx.serialization.Serializable

interface GameDetailsRootPresenter : BasePresenter<GameDetailsRootViewState, GameDetailsRootIntent>,
    RootDecomposeComponent<GameDetailsRootPresenter.Child, GameDetailsRootPresenter.Config> {

    sealed interface Child {
        data class GameDetails(val component: GameDetailsPresenter) : Child
    }

    @Serializable
    sealed interface Config {
        @Serializable
        data class GameDetails(val game: GameUiModel) : Config
    }
}