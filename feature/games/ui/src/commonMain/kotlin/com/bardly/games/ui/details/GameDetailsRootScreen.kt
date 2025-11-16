package com.bardly.games.ui.details

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import com.arkivanov.decompose.ExperimentalDecomposeApi
import com.bardly.chats.ui.chat.ChatScreen
import com.retro99.base.ui.decompose.RootChildStack

@OptIn(ExperimentalDecomposeApi::class, ExperimentalMaterial3Api::class)
@Composable
fun GameDetailsRootScreen(
    component: GameDetailsRootPresenter,
) {
    RootChildStack(
        component,
    ) { child ->
        when (val screen = child.instance) {
            is GameDetailsRootPresenter.Child.GameDetails -> GameDetailsScreen(screen.component)
            is GameDetailsRootPresenter.Child.Chat -> ChatScreen(screen.component)
        }
    }
}
