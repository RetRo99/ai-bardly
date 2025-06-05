package com.bardly.games.ui.details

import androidx.compose.runtime.Composable
import com.arkivanov.decompose.ExperimentalDecomposeApi
import com.retro99.base.ui.decompose.RootChildStack

@OptIn(ExperimentalDecomposeApi::class)
@Composable
fun GameDetailsRootScreen(
    component: GameDetailsRootPresenter,
) {
    RootChildStack(
        component,
    ) { child ->
        when (val screen = child.instance) {
            is GameDetailsRootPresenter.Child.GameDetails -> GameDetailsScreen(screen.component)
        }
    }
}