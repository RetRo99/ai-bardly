package com.ai.bardly.feature.main.chats.root

import androidx.compose.runtime.Composable
import com.ai.bardly.decompose.RootChildStack
import com.ai.bardly.feature.main.chats.ui.chat.ChatScreen
import com.ai.bardly.feature.main.chats.ui.recent.RecentChatsScreen

@Composable
fun RootRecentScreen(
    component: RootRecentPresenter,
) {
    RootChildStack(
        component
    ) { child ->
        when (val screen = child.instance) {
            is RootRecentPresenter.Child.RecentChats -> RecentChatsScreen(screen.component)
            is RootRecentPresenter.Child.Chat -> ChatScreen(screen.component)
        }
    }
}