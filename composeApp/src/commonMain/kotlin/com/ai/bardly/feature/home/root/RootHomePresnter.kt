package com.ai.bardly.feature.home.root

import com.ai.bardly.base.BaseComponent
import com.ai.bardly.feature.chats.ui.chat.ChatComponent
import com.ai.bardly.feature.games.ui.details.GameDetailsComponent
import com.ai.bardly.feature.games.ui.model.GameUiModel
import com.ai.bardly.feature.home.ui.HomeComponent
import com.ai.bardly.navigation.root.RootDecomposeComponent
import kotlinx.serialization.Serializable

interface RootHomePresnter : BaseComponent<RootHomeViewState, RootHomeIntent>,
    RootDecomposeComponent<RootHomePresnter.HomeChild, RootHomePresnter.HomeConfig> {
    sealed interface HomeChild {
        data class Home(val component: HomeComponent) : HomeChild
        data class GameDetails(val component: GameDetailsComponent) : HomeChild
        data class Chat(val component: ChatComponent) : HomeChild
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
