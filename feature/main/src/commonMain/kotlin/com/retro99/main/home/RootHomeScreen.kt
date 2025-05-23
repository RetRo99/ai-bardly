package com.retro99.main.home

import androidx.compose.runtime.Composable
import com.arkivanov.decompose.ExperimentalDecomposeApi
import com.bardly.chats.ui.chat.ChatScreen
import com.bardly.games.ui.details.GameDetailsScreen
import com.bardly.home.ui.HomeScreen
import com.retro99.base.ui.decompose.RootChildStack

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