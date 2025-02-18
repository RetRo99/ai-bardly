package com.ai.bardly.feature.main.games.root

import com.ai.bardly.base.BasePresenter
import com.ai.bardly.feature.main.chats.ui.chat.ChatPresenter
import com.ai.bardly.feature.main.games.ui.details.GameDetailsPresenter
import com.ai.bardly.feature.main.games.ui.list.GamesListComponent
import com.ai.bardly.feature.main.games.ui.model.GameUiModel
import com.ai.bardly.navigation.root.RootDecomposeComponent
import kotlinx.serialization.Serializable

interface RootGamesPresenter : BasePresenter<RootGamesViewState, RootGamesIntent>,
    RootDecomposeComponent<RootGamesPresenter.Child, RootGamesPresenter.Config> {

    sealed interface Child {
        data class GamesList(val component: GamesListComponent) : Child
        data class GameDetails(val component: GameDetailsPresenter) : Child
        data class ChatDetails(val component: ChatPresenter) : Child
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
