package com.ai.bardly.feature.main.home.root

import androidx.compose.runtime.Composable
import com.ai.bardly.decompose.RootChildStack
import com.ai.bardly.feature.main.chats.ui.chat.ChatScreen
import com.ai.bardly.feature.main.games.ui.details.GameDetailsScreen
import com.ai.bardly.feature.main.home.ui.HomeScreen
import com.arkivanov.decompose.ExperimentalDecomposeApi

@OptIn(ExperimentalDecomposeApi::class)
@Composable
fun RootHomeScreen(
    component: RootHomePresenter,
) {
    RootChildStack(
        root = component,
    ) { child ->
        when (val screen = child.instance) {
            is RootHomePresenter.Child.Home -> HomeScreen(screen.component)
            is RootHomePresenter.Child.GameDetails -> GameDetailsScreen(screen.component)
            is RootHomePresenter.Child.Chat -> ChatScreen(screen.component)
        }
    }
}