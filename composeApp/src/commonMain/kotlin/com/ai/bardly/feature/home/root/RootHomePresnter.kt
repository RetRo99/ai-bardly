package com.ai.bardly.feature.home.root

import com.ai.bardly.base.BasePresenter
import com.ai.bardly.feature.chats.ui.chat.ChatPresenter
import com.ai.bardly.feature.games.ui.details.GameDetailsPresenter
import com.ai.bardly.feature.games.ui.model.GameUiModel
import com.ai.bardly.feature.home.ui.HomePresenter
import com.ai.bardly.navigation.root.RootDecomposeComponent
import kotlinx.serialization.Serializable

interface RootHomePresnter : BasePresenter<RootHomeViewState, RootHomeIntent>,
    RootDecomposeComponent<RootHomePresnter.HomeChild, RootHomePresnter.HomeConfig> {
    sealed interface HomeChild {
        data class Home(val component: HomePresenter) : HomeChild
        data class GameDetails(val component: GameDetailsPresenter) : HomeChild
        data class Chat(val component: ChatPresenter) : HomeChild
    }

    @Serializable
    sealed interface HomeConfig {
        @Serializable
        data object Home : HomeConfig

        @Serializable
        data class GameDetails(val game: GameUiModel) : HomeConfig

        @Serializable
        data class Chat(val title: String, val id: Int) : HomeConfig
    }
}
