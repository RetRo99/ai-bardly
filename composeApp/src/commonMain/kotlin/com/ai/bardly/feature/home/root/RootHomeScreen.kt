package com.ai.bardly.feature.home.root

import androidx.compose.runtime.Composable
import com.ai.bardly.feature.games.ui.details.GameDetailsScreen
import com.ai.bardly.feature.home.ui.HomeScreen
import com.ai.bardly.util.backAnimation
import com.arkivanov.decompose.ExperimentalDecomposeApi
import com.arkivanov.decompose.extensions.compose.experimental.stack.ChildStack

@OptIn(ExperimentalDecomposeApi::class)
@Composable
fun RootHomeScreen(
    component: RootHomeComponent,
) {
    ChildStack(
        component.childStack,
        animation = backAnimation(
            backHandler = component.backHandler,
            onBack = component::onBackClicked,
        )
    ) { child ->
        when (val screen = child.instance) {
            is RootHomeComponent.HomeChild.Home -> HomeScreen(screen.component)
            is RootHomeComponent.HomeChild.GameDetails -> GameDetailsScreen(screen.component)
        }
    }
}