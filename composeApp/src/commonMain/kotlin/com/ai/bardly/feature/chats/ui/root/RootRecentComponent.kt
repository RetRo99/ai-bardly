package com.ai.bardly.feature.chats.ui.root

import com.ai.bardly.base.BaseComponent
import com.ai.bardly.feature.chats.ui.chat.ChatComponent
import com.ai.bardly.feature.chats.ui.recent.RecentChatsComponent
import com.ai.bardly.navigation.root.RootComponent
import kotlinx.serialization.Serializable

interface RootRecentComponent : BaseComponent<RootRecentViewState, RootRecentIntent>,
    RootComponent<RootRecentComponent.RootRecentChild> {

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
