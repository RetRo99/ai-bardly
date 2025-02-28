package com.retro99.main.games

import com.bardly.chats.ui.chat.ChatPresenter
import com.bardly.games.ui.details.GameDetailsPresenter
import com.bardly.games.ui.list.GamesListComponent
import com.bardly.games.ui.model.GameUiModel
import com.retro99.base.ui.BasePresenter
import com.retro99.base.ui.decompose.RootDecomposeComponent
import kotlinx.serialization.Serializable

interface RootGamesPresenter : BasePresenter<RootGamesViewState, RootGamesIntent>,
    RootDecomposeComponent<RootGamesPresenter.Child, RootGamesPresenter.Config> {

    sealed interface Child {
        data class GamesList(val component: GamesListComponent) : Child
        data class GameDetails(val component: GameDetailsPresenter) : Child
        data class Chat(val component: ChatPresenter) : Child
    }

    @Serializable
    sealed interface Config {
        @Serializable
        data object GamesList : Config

        @Serializable
        data class GameDetails(val game: GameUiModel) : Config

        @Serializable
        data class Chat(val title: String, val id: Int) : Config
    }
}
