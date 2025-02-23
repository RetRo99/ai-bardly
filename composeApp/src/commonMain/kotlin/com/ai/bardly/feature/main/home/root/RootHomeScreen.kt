package com.ai.bardly.feature.main.home.root

import androidx.compose.runtime.Composable
import com.ai.bardly.decompose.RootChildStack
import com.ai.bardly.feature.main.home.ui.HomeScreen
import com.arkivanov.decompose.ExperimentalDecomposeApi
import com.bardly.chats.ui.chat.ChatScreen
import com.bardly.games.ui.details.GameDetailsScreen

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