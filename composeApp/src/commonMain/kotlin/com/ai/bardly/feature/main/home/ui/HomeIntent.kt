package com.ai.bardly.feature.main.home.ui

import com.ai.bardly.base.ScreenIntent
import com.ai.bardly.feature.main.games.ui.model.GameUiModel

sealed interface HomeIntent : ScreenIntent {
    data class OpenChatClicked(val gameTitle: String, val gameId: Int) : HomeIntent
    data class OpenGameDetails(val game: GameUiModel) : HomeIntent
}