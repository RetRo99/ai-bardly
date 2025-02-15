package com.ai.bardly.feature.home.root

import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import com.ai.bardly.feature.chats.ui.chat.ChatScreen
import com.ai.bardly.feature.games.ui.details.GameDetailsScreen
import com.ai.bardly.feature.home.ui.HomeScreen
import com.ai.bardly.navigation.root.RootComponent
import com.ai.bardly.util.LocalScreenAnimationScope
import com.ai.bardly.util.LocalScreenTransitionScope
import com.arkivanov.decompose.Child
import com.arkivanov.decompose.ExperimentalDecomposeApi
import com.arkivanov.decompose.extensions.compose.experimental.stack.ChildStack
import com.arkivanov.decompose.extensions.compose.experimental.stack.animation.PredictiveBackParams
import com.arkivanov.decompose.extensions.compose.experimental.stack.animation.fade
import com.arkivanov.decompose.extensions.compose.experimental.stack.animation.plus
import com.arkivanov.decompose.extensions.compose.experimental.stack.animation.scale
import com.arkivanov.decompose.extensions.compose.experimental.stack.animation.stackAnimation
import com.arkivanov.decompose.extensions.compose.stack.animation.predictiveback.materialPredictiveBackAnimatable

@Composable
fun RootHomeScreen(
    component: RootHomeComponent,
) {
    RootChildStack(
        root = component,
    ) { child ->
        when (val screen = child.instance) {
            is RootHomeComponent.HomeChild.Home -> HomeScreen(screen.component)
            is RootHomeComponent.HomeChild.GameDetails -> GameDetailsScreen(screen.component)
            is RootHomeComponent.HomeChild.Chat -> ChatScreen(screen.component)
        }
    }
}

@OptIn(ExperimentalDecomposeApi::class, ExperimentalSharedTransitionApi::class)
@Composable
fun <RootChild : Any, Config : Any> RootChildStack(
    root: RootComponent<RootChild, Config>,
    modifier: Modifier = Modifier,
    content: @Composable AnimatedVisibilityScope.(child: Child.Created<Config, RootChild>) -> Unit,
) {
    SharedTransitionLayout {
        ChildStack(stack = root.childStack, modifier = modifier,
            animation = stackAnimation(
                animator = fade() + scale(),
                predictiveBackParams = {
                    PredictiveBackParams(
                        backHandler = root.backHandler,
                        onBack = root::onBackClicked,
                        animatable = ::materialPredictiveBackAnimatable,
                    )
                }
            )) {
            CompositionLocalProvider(
                LocalScreenTransitionScope provides this@SharedTransitionLayout,
                LocalScreenAnimationScope provides this@ChildStack,
            ) {
                content(it)
            }
        }
    }
}
