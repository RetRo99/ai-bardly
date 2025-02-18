package com.ai.bardly.feature.main.games.root

import androidx.compose.runtime.Composable
import com.ai.bardly.decompose.RootChildStack
import com.ai.bardly.feature.main.chats.ui.chat.ChatScreen
import com.ai.bardly.feature.main.games.ui.details.GameDetailsScreen
import com.ai.bardly.feature.main.games.ui.list.GamesListScreen

@Composable
fun RootGamesScreen(
    component: RootGamesPresenter,
) {
    RootChildStack(
        component,
    ) { child ->
        when (val screen = child.instance) {
            is RootGamesPresenter.Child.GamesList -> GamesListScreen(screen.component)
            is RootGamesPresenter.Child.GameDetails -> GameDetailsScreen(screen.component)
            is RootGamesPresenter.Child.ChatDetails -> ChatScreen(screen.component)
        }
    }
}