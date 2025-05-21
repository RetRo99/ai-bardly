package com.bardly.games.ui.list

import com.bardly.games.ui.model.GameUiModel
import com.retro99.base.ui.BaseScreenIntent

sealed interface GamesListIntent : BaseScreenIntent {
    data class GameClicked(val game: GameUiModel) : GamesListIntent
    data class OpenChatClicked(val gameTitle: String, val gameId: String) : GamesListIntent
    data class SearchQueryChanged(val query: String) : GamesListIntent
    data class SearchStateChanged(val isActive: Boolean) : GamesListIntent
}
