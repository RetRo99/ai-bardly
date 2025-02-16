package com.ai.bardly.feature.main.chats.ui.root

import com.ai.bardly.base.BasePresenter
import com.ai.bardly.feature.main.chats.ui.chat.ChatPresenter
import com.ai.bardly.feature.main.chats.ui.recent.RecentChatsComponent
import com.ai.bardly.navigation.root.RootDecomposeComponent
import kotlinx.serialization.Serializable

interface RootRecentPresenter : BasePresenter<RootRecentViewState, RootRecentIntent>,
    RootDecomposeComponent<RootRecentPresenter.RootRecentChild, RootRecentPresenter.RootRecentConfig> {

    sealed interface RootRecentChild {
        data class Chat(val component: ChatPresenter) : RootRecentChild
        data class RecentChats(val component: RecentChatsComponent) : RootRecentChild
    }

    @Serializable
    sealed interface RootRecentConfig {
        @Serializable
        data object RecentChats : RootRecentConfig

        @Serializable
        data class Chat(val title: String, val id: Int) : RootRecentConfig
    }
}
