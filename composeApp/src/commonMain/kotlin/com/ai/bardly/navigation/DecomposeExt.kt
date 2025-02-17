package com.ai.bardly.navigation

import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import com.ai.bardly.navigation.root.RootDecomposeComponent
import com.ai.bardly.util.LocalScreenAnimationScope
import com.ai.bardly.util.LocalScreenTransitionScope
import com.arkivanov.decompose.Child
import com.arkivanov.decompose.ExperimentalDecomposeApi
import com.arkivanov.decompose.extensions.compose.experimental.stack.ChildStack
import com.arkivanov.decompose.router.stack.StackNavigator

@OptIn(ExperimentalDecomposeApi::class, ExperimentalSharedTransitionApi::class)
@Composable
fun <RootChild : Any, Config : Any> RootChildStack(
    root: RootDecomposeComponent<RootChild, Config>,
    modifier: Modifier = Modifier,
    content: @Composable AnimatedVisibilityScope.(child: Child.Created<Config, RootChild>) -> Unit,
) {
    SharedTransitionLayout {
        ChildStack(
            stack = root.childStack, modifier = modifier,
            animation = backAnimation(
                backHandler = root.backHandler,
                onBack = root::onBackClicked,
            ),
        ) {
            CompositionLocalProvider(
                LocalScreenTransitionScope provides this@SharedTransitionLayout,
                LocalScreenAnimationScope provides this@ChildStack,
            ) {
                content(it)
            }
        }
    }
}


fun <C : Any> StackNavigator<C>.switchTab(configuration: C, onComplete: () -> Unit = {}) {
    navigate(
        transformer = { stack ->
            val existing = stack.find { it::class == configuration::class }
            if (existing != null) {
                stack.filterNot { it::class == configuration::class } + existing
            } else {
                stack + configuration
            }
        },
        onComplete = { _, _ -> onComplete() },
    )
}