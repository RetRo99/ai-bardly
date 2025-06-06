package com.bardly.home.ui

import com.bardly.games.ui.model.GameUiModel
import com.retro99.base.ui.BaseScreenIntent

sealed interface HomeIntent : BaseScreenIntent {
    data class OpenChatClicked(val gameTitle: String, val gameId: String) : HomeIntent
    data class OpenGameDetails(val game: GameUiModel) : HomeIntent
    data object NavigateToGamesList : HomeIntent
}
