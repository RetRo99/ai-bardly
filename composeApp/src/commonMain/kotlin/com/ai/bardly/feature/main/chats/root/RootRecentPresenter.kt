package com.ai.bardly.feature.main.chats.root

import com.bardly.chats.ui.chat.ChatPresenter
import com.bardly.chats.ui.recent.RecentChatsComponent
import com.retro99.base.ui.BasePresenter
import com.retro99.base.ui.decompose.RootDecomposeComponent
import kotlinx.serialization.Serializable

interface RootRecentPresenter : BasePresenter<RootRecentViewState, RootRecentIntent>,
    RootDecomposeComponent<RootRecentPresenter.Child, RootRecentPresenter.Config> {

    sealed interface Child {
        data class Chat(val component: ChatPresenter) : Child
        data class RecentChats(val component: RecentChatsComponent) : Child
    }

    @Serializable
    sealed interface Config {
        @Serializable
        data object RecentChats : Config

        @Serializable
        data class Chat(val title: String, val id: Int) : Config
    }
}
