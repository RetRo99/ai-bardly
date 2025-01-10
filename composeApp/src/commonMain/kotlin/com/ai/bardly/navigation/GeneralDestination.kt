package com.ai.bardly.navigation

import androidx.compose.runtime.Composable
import com.ai.bardly.GameUiModel
import com.ai.bardly.screens.games.details.GameDetailsScreen

sealed class GeneralDestination(
    val screen: @Composable () -> Unit,
) {

    data class GameDetail(
        val game: GameUiModel,
    ) : GeneralDestination(
        screen = {
            GameDetailsScreen(game)
        }
    )
}