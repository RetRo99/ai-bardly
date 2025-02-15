package com.ai.bardly.feature.home.root

import androidx.compose.runtime.Composable
import com.ai.bardly.feature.chats.ui.chat.ChatScreen
import com.ai.bardly.feature.games.ui.details.GameDetailsScreen
import com.ai.bardly.feature.home.ui.HomeScreen
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
fun RootHomeScreen(
    component: RootHomeComponent,
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
        ),
    ) { child ->
        when (val screen = child.instance) {
            is RootHomeComponent.HomeChild.Home -> HomeScreen(screen.component)
            is RootHomeComponent.HomeChild.GameDetails -> GameDetailsScreen(screen.component)
            is RootHomeComponent.HomeChild.Chat -> ChatScreen(screen.component)
        }
    }
}