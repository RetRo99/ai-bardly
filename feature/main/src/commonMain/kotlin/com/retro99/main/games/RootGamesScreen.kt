package com.retro99.main.games

import androidx.compose.runtime.Composable
import com.arkivanov.decompose.ExperimentalDecomposeApi
import com.bardly.games.ui.details.GameDetailsRootScreen
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
            is RootGamesPresenter.Child.RootGameDetails -> GameDetailsRootScreen(screen.component)
        }
    }
}