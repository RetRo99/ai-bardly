package com.ai.bardly.feature.games.root

import androidx.compose.runtime.Composable
import com.ai.bardly.feature.chats.ui.chat.ChatScreen
import com.ai.bardly.feature.games.ui.details.GameDetailsScreen
import com.ai.bardly.feature.games.ui.list.GamesListScreen
import com.arkivanov.decompose.ExperimentalDecomposeApi
import com.arkivanov.decompose.extensions.compose.experimental.stack.ChildStack
import com.arkivanov.decompose.extensions.compose.experimental.stack.animation.PredictiveBackParams
import com.arkivanov.decompose.extensions.compose.experimental.stack.animation.fade
import com.arkivanov.decompose.extensions.compose.experimental.stack.animation.plus
import com.arkivanov.decompose.extensions.compose.experimental.stack.animation.scale
import com.arkivanov.decompose.extensions.compose.experimental.stack.animation.stackAnimation
import com.arkivanov.decompose.extensions.compose.stack.animation.predictiveback.androidPredictiveBackAnimatable

@OptIn(ExperimentalDecomposeApi::class)
@Composable
fun RootGamesScreen(
    component: RootGamesComponent,
) {
    ChildStack(
        component.childStack,
        animation = stackAnimation(
            animator = fade() + scale(),
            predictiveBackParams = {
                PredictiveBackParams(
                    backHandler = component.backHandler,
                    onBack = component::onBackClicked,
                    animatable = ::androidPredictiveBackAnimatable,
                )
            }
        )
    ) { child ->
        when (val screen = child.instance) {
            is RootGamesComponent.GamesChild.GamesList -> GamesListScreen(screen.component)
            is RootGamesComponent.GamesChild.GameDetails -> GameDetailsScreen(screen.component)
            is RootGamesComponent.GamesChild.ChatDetails -> ChatScreen(screen.component)
        }
    }
}