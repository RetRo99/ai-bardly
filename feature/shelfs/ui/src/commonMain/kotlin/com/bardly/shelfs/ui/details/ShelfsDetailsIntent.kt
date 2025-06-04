package com.bardly.shelfs.ui.details

import com.bardly.games.ui.model.GameUiModel
import com.retro99.base.ui.BaseScreenIntent

sealed interface ShelfDetailsIntent : BaseScreenIntent {
    data object NavigateBack : ShelfDetailsIntent
    data class GameClicked(val game: GameUiModel) : ShelfDetailsIntent
    data object DeleteShelf : ShelfDetailsIntent
}
