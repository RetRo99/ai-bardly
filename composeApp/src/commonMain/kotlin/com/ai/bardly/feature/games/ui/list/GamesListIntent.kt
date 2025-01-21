package com.ai.bardly.feature.games.ui.list

import com.ai.bardly.base.ScreenIntent
import com.ai.bardly.feature.games.ui.model.GameUiModel

sealed interface GamesListIntent : ScreenIntent {
    object NavigateBack : GamesListIntent
    data class GameClicked(val game: GameUiModel) : GamesListIntent
    data class OpenChatClicked(val gameTitle: String, val gameId: Int) : GamesListIntent
    data class SearchQueryChanged(val query: String) : GamesListIntent
    data class SearchStateChanged(val isActive: Boolean) : GamesListIntent
}