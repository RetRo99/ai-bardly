package com.ai.bardly.feature.chats.ui.root

import androidx.compose.runtime.Composable
import com.ai.bardly.feature.chats.ui.chat.ChatScreen
import com.ai.bardly.feature.chats.ui.recent.RecentChatsScreen
import com.ai.bardly.navigation.RootChildStack

@Composable
fun RootRecentScreen(
    component: RootRecentComponent,
) {
    RootChildStack(
        component
    ) { child ->
        when (val screen = child.instance) {
            is RootRecentComponent.RootRecentChild.RecentChats -> RecentChatsScreen(screen.component)
            is RootRecentComponent.RootRecentChild.Chat -> ChatScreen(screen.component)
        }
    }
}