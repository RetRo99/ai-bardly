package com.ai.bardly.feature.main.chats.root

import androidx.compose.runtime.Composable
import com.ai.bardly.feature.main.chats.ui.chat.ChatScreen
import com.ai.bardly.feature.main.chats.ui.recent.RecentChatsScreen
import com.ai.bardly.navigation.RootChildStack

@Composable
fun RootRecentScreen(
    component: RootRecentPresenter,
) {
    RootChildStack(
        component
    ) { child ->
        when (val screen = child.instance) {
            is RootRecentPresenter.RootRecentChild.RecentChats -> RecentChatsScreen(screen.component)
            is RootRecentPresenter.RootRecentChild.Chat -> ChatScreen(screen.component)
        }
    }
}