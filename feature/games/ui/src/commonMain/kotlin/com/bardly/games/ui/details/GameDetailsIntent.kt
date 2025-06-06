package com.bardly.games.ui.details

import com.retro99.base.ui.BaseScreenIntent

sealed interface GameDetailsIntent : BaseScreenIntent {
    data object NavigateBack : GameDetailsIntent
    data object OpenChatClicked : GameDetailsIntent
    data class OnChangeFavoriteClicked(val isFavoriteNew: Boolean) : GameDetailsIntent
    data class AddGameToShelf(val shelfId: String, val shelfName: String) : GameDetailsIntent
    data object ShowShelfSelectionDialog : GameDetailsIntent
    data object HideShelfSelectionDialog : GameDetailsIntent
}
