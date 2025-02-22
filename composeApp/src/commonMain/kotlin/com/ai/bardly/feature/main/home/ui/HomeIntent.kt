package com.ai.bardly.feature.main.home.ui

import com.ai.bardly.feature.main.games.ui.model.GameUiModel
import com.retro99.base.BaseScreenIntent

sealed interface HomeIntent : BaseScreenIntent {
    data class OpenChatClicked(val gameTitle: String, val gameId: Int) : HomeIntent
    data class OpenGameDetails(val game: GameUiModel) : HomeIntent
}