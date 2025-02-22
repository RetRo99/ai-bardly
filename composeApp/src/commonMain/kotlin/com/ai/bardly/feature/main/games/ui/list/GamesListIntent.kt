package com.ai.bardly.feature.main.games.ui.list

import com.ai.bardly.feature.main.games.ui.model.GameUiModel
import com.retro99.base.BaseScreenIntent

sealed interface GamesListIntent : BaseScreenIntent {
    data class GameClicked(val game: GameUiModel) : GamesListIntent
    data class OpenChatClicked(val gameTitle: String, val gameId: Int) : GamesListIntent
    data class SearchQueryChanged(val query: String) : GamesListIntent
    data class SearchStateChanged(val isActive: Boolean) : GamesListIntent
}