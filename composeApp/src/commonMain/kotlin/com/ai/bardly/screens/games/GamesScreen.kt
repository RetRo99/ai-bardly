package com.ai.bardly.screens.games

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.ai.bardly.screens.EmptyScreenContent
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun GamesScreen(
) {
    val viewModel = koinViewModel<GamesViewModel>()
    EmptyScreenContent(Modifier.fillMaxSize(), "GAMES")
}