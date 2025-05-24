package com.retro99.main.shelfs

import com.bardly.chats.ui.chat.ChatPresenter
import com.bardly.shelfs.ui.details.ShelfDetailsPresenter
import com.bardly.shelfs.ui.list.ShelfsListPresenter
import com.bardly.shelfs.ui.model.ShelfUiModel
import com.bardly.games.ui.details.GameDetailsPresenter
import com.bardly.games.ui.model.GameUiModel
import com.retro99.base.ui.BasePresenter
import com.retro99.base.ui.decompose.RootDecomposeComponent
import kotlinx.serialization.Serializable

interface RootShelfsPresenter : BasePresenter<RootShelfsViewState, RootShelfsIntent>,
    RootDecomposeComponent<RootShelfsPresenter.Child, RootShelfsPresenter.Config> {

    sealed interface Child {
        data class ShelfsList(val component: ShelfsListPresenter) : Child
        data class ShelfDetails(val component: ShelfDetailsPresenter) : Child
        data class GameDetails(val component: GameDetailsPresenter) : Child
        data class Chat(val component: ChatPresenter) : Child
    }

    @Serializable
    sealed interface Config {
        @Serializable
        data object ShelfsList : Config

        @Serializable
        data class ShelfDetails(val shelf: ShelfUiModel) : Config

        @Serializable
        data class GameDetails(val game: GameUiModel) : Config

        @Serializable
        data class Chat(val title: String, val id: String) : Config
    }
}
