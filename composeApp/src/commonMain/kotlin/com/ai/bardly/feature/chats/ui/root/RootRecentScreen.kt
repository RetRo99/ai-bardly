package com.ai.bardly.feature.chats.ui.root

import androidx.compose.runtime.Composable
import com.ai.bardly.feature.chats.ui.chat.ChatScreen
import com.ai.bardly.feature.chats.ui.recent.RecentChatsScreen
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
fun RootRecentScreen(
    component: RootRecentComponent,
) {
    ChildStack(
        component.childStack,
        animation = stackAnimation(
            animator = fade() + scale(), predictiveBackParams = {
                PredictiveBackParams(
                    backHandler = component.backHandler,
                    onBack = component::onBackClicked,
                    animatable = ::androidPredictiveBackAnimatable,
                )
            }
        )
    ) { child ->
        when (val screen = child.instance) {
            is RootRecentComponent.RootRecentChild.RecentChats -> RecentChatsScreen(screen.component)
            is RootRecentComponent.RootRecentChild.Chat -> ChatScreen(screen.component)
        }
    }
}