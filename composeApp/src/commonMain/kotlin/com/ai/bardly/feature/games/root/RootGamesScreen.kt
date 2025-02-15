package com.ai.bardly.feature.games.root

import androidx.compose.runtime.Composable
import com.ai.bardly.feature.chats.ui.chat.ChatScreen
import com.ai.bardly.feature.games.ui.details.GameDetailsScreen
import com.ai.bardly.feature.games.ui.list.GamesListScreen
import com.ai.bardly.navigation.RootChildStack

@Composable
fun RootGamesScreen(
    component: RootGamesPresenter,
) {
    RootChildStack(
        component,
    ) { child ->
        when (val screen = child.instance) {
            is RootGamesPresenter.GamesChild.GamesList -> GamesListScreen(screen.component)
            is RootGamesPresenter.GamesChild.GameDetails -> GameDetailsScreen(screen.component)
            is RootGamesPresenter.GamesChild.ChatDetails -> ChatScreen(screen.component)
        }
    }
}