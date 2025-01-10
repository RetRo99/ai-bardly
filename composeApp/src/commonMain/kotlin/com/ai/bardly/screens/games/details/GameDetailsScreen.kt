package com.ai.bardly.screens.games.details

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.ai.bardly.GameUiModel
import com.ai.bardly.screens.EmptyScreenContent
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun GameDetailsScreen(
    game: GameUiModel
) {
    val viewModel = koinViewModel<GameDetailsViewModel>()
    GamesScreenContent(game)
}

@Composable
fun GamesScreenContent(game: GameUiModel) {
    EmptyScreenContent(Modifier.fillMaxSize(), "GAME DETAILS")
}
