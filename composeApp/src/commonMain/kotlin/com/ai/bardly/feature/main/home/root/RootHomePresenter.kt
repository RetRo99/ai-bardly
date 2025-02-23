package com.ai.bardly.feature.main.home.root

import com.bardly.chats.ui.chat.ChatPresenter
import com.bardly.games.ui.details.GameDetailsPresenter
import com.bardly.games.ui.model.GameUiModel
import com.bardly.home.ui.HomePresenter
import com.retro99.base.ui.BasePresenter
import com.retro99.base.ui.decompose.RootDecomposeComponent
import kotlinx.serialization.Serializable

interface RootHomePresenter : BasePresenter<RootHomeViewState, RootHomeIntent>,
    RootDecomposeComponent<RootHomePresenter.Child, RootHomePresenter.Config> {
    sealed interface Child {
        data class Home(val component: HomePresenter) : Child
        data class GameDetails(val component: GameDetailsPresenter) : Child
        data class Chat(val component: ChatPresenter) : Child
    }

    @Serializable
    sealed interface Config {
        @Serializable
        data object Home : Config

        @Serializable
        data class GameDetails(val game: GameUiModel) : Config

        @Serializable
        data class Chat(val title: String, val id: Int) : Config
    }
}
