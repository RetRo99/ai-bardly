package com.ai.bardly.feature.main.home.root

import androidx.compose.runtime.Composable
import com.ai.bardly.feature.main.chats.ui.chat.ChatScreen
import com.ai.bardly.feature.main.games.ui.details.GameDetailsScreen
import com.ai.bardly.feature.main.home.ui.HomeScreen
import com.ai.bardly.navigation.RootChildStack

@Composable
fun RootHomeScreen(
    component: RootHomePresenter,
) {
    RootChildStack(
        root = component,
    ) { child ->
        when (val screen = child.instance) {
            is RootHomePresenter.HomeChild.Home -> HomeScreen(screen.component)
            is RootHomePresenter.HomeChild.GameDetails -> GameDetailsScreen(screen.component)
            is RootHomePresenter.HomeChild.Chat -> ChatScreen(screen.component)
        }
    }
}