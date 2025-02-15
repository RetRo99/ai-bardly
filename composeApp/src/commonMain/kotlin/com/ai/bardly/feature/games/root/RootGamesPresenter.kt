package com.ai.bardly.feature.games.root

import com.ai.bardly.base.BasePresenter
import com.ai.bardly.feature.chats.ui.chat.ChatComponent
import com.ai.bardly.feature.games.ui.details.GameDetailsPresenter
import com.ai.bardly.feature.games.ui.list.GamesListComponent
import com.ai.bardly.feature.games.ui.model.GameUiModel
import com.ai.bardly.navigation.root.RootDecomposeComponent
import kotlinx.serialization.Serializable

interface RootGamesPresenter : BasePresenter<RootGamesViewState, RootGamesIntent>,
    RootDecomposeComponent<RootGamesPresenter.GamesChild, RootGamesPresenter.GamesConfig> {

    sealed interface GamesChild {
        data class GamesList(val component: GamesListComponent) : GamesChild
        data class GameDetails(val component: GameDetailsPresenter) : GamesChild
        data class ChatDetails(val component: ChatComponent) : GamesChild
    }

    @Serializable
    sealed interface GamesConfig {
        @Serializable
        data object GamesList : GamesConfig

        @Serializable
        data class GameDetails(val game: GameUiModel) : GamesConfig

        @Serializable
        data class Chat(val title: String, val id: Int) : GamesConfig
    }
}
