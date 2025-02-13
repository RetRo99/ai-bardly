package com.ai.bardly.feature.games.root

import androidx.compose.runtime.Composable
import com.ai.bardly.feature.games.ui.details.GameDetailsScreen
import com.ai.bardly.feature.games.ui.list.GamesListScreen
import com.ai.bardly.util.backAnimation
import com.arkivanov.decompose.ExperimentalDecomposeApi
import com.arkivanov.decompose.extensions.compose.experimental.stack.ChildStack

@OptIn(ExperimentalDecomposeApi::class)
@Composable
fun RootGamesScreen(
    component: RootGamesComponent,
) {
    ChildStack(
        component.childStack,
        animation = backAnimation(
            backHandler = component.backHandler,
            onBack = component::onBackClicked,
        )
    ) { child ->
        when (val screen = child.instance) {
            is RootGamesComponent.GamesChild.GamesList -> GamesListScreen(screen.component)
            is RootGamesComponent.GamesChild.GameDetails -> GameDetailsScreen(screen.component)
        }
    }
}