package com.ai.bardly.feature.chats.ui.root

import com.ai.bardly.base.BaseComponent
import com.ai.bardly.feature.chats.ui.chat.ChatComponent
import com.ai.bardly.feature.chats.ui.recent.RecentChatsComponent
import com.ai.bardly.navigation.root.RootDecomposeComponent
import kotlinx.serialization.Serializable

interface RootRecentPresenter : BaseComponent<RootRecentViewState, RootRecentIntent>,
    RootDecomposeComponent<RootRecentPresenter.RootRecentChild, RootRecentPresenter.RootRecentConfig> {

    sealed interface RootRecentChild {
        data class Chat(val component: ChatComponent) : RootRecentChild
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
