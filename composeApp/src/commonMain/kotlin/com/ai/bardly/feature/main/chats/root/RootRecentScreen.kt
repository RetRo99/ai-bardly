package com.ai.bardly.feature.main.chats.root

import androidx.compose.runtime.Composable
import com.arkivanov.decompose.ExperimentalDecomposeApi
import com.bardly.chats.ui.chat.ChatScreen
import com.bardly.chats.ui.recent.RecentChatsScreen
import com.retro99.base.ui.decompose.RootChildStack

@OptIn(ExperimentalDecomposeApi::class)
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