package com.ai.bardly.feature.games.root

import androidx.compose.runtime.Composable
import com.ai.bardly.feature.chats.ui.chat.ChatScreen
import com.ai.bardly.feature.games.ui.details.GameDetailsScreen
import com.ai.bardly.feature.games.ui.list.GamesListScreen
import com.ai.bardly.feature.home.root.RootChildStack

@Composable
fun RootGamesScreen(
    component: RootGamesComponent,
) {
    RootChildStack(
        component,
    ) { child ->
        when (val screen = child.instance) {
            is RootGamesComponent.GamesChild.GamesList -> GamesListScreen(screen.component)
            is RootGamesComponent.GamesChild.GameDetails -> GameDetailsScreen(screen.component)
            is RootGamesComponent.GamesChild.ChatDetails -> ChatScreen(screen.component)
        }
    }
}