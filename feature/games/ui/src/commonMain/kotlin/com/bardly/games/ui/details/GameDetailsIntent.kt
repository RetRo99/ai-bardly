package com.bardly.games.ui.details

import com.retro99.base.ui.BaseScreenIntent

sealed interface GameDetailsIntent : BaseScreenIntent {
    data object NavigateBack : GameDetailsIntent
    data object OpenChatClicked : GameDetailsIntent
    data class OnChangeFavoriteClicked(val isFavoriteNew: Boolean) : GameDetailsIntent
}