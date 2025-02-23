package com.ai.bardly.feature.main.games.root

import androidx.compose.runtime.Composable
import com.arkivanov.decompose.ExperimentalDecomposeApi
import com.bardly.chats.ui.chat.ChatScreen
import com.bardly.games.ui.details.GameDetailsScreen
import com.bardly.games.ui.list.GamesListScreen
import com.retro99.base.ui.decompose.RootChildStack

@OptIn(ExperimentalDecomposeApi::class)
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