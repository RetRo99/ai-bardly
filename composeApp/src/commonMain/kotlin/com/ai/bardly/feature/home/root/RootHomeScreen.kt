package com.ai.bardly.feature.home.root

import androidx.compose.runtime.Composable
import com.ai.bardly.feature.chats.ui.chat.ChatScreen
import com.ai.bardly.feature.games.ui.details.GameDetailsScreen
import com.ai.bardly.feature.home.ui.HomeScreen
import com.ai.bardly.navigation.RootChildStack

@Composable
fun RootHomeScreen(
    component: RootHomePresnter,
) {
    RootChildStack(
        root = component,
    ) { child ->
        when (val screen = child.instance) {
            is RootHomePresnter.HomeChild.Home -> HomeScreen(screen.component)
            is RootHomePresnter.HomeChild.GameDetails -> GameDetailsScreen(screen.component)
            is RootHomePresnter.HomeChild.Chat -> ChatScreen(screen.component)
        }
    }
}