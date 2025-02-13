package com.ai.bardly.feature.chats.ui.root

import androidx.compose.runtime.Composable
import com.ai.bardly.feature.chats.ui.chat.ChatScreen
import com.ai.bardly.feature.chats.ui.recent.RecentChatsScreen
import com.ai.bardly.util.backAnimation
import com.arkivanov.decompose.ExperimentalDecomposeApi
import com.arkivanov.decompose.extensions.compose.experimental.stack.ChildStack

@OptIn(ExperimentalDecomposeApi::class)
@Composable
fun RootRecentScreen(
    component: RootRecentComponent,
) {
    ChildStack(
        component.childStack,
        animation = backAnimation(
            backHandler = component.backHandler,
            onBack = component::onBackClicked,
        )
    ) { child ->
        when (val screen = child.instance) {
            is RootRecentComponent.RootRecentChild.RecentChats -> RecentChatsScreen(screen.component)
            is RootRecentComponent.RootRecentChild.Chat -> ChatScreen(screen.component)
        }
    }
}