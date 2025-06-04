package com.bardly.shelfs.ui.details

import com.bardly.games.ui.model.GameUiModel
import com.retro99.base.ui.BaseScreenIntent

sealed interface ShelfDetailsIntent : BaseScreenIntent {
    data object NavigateBack : ShelfDetailsIntent
    data class GameClicked(val game: GameUiModel) : ShelfDetailsIntent
    data object ShowDeleteConfirmationDialog : ShelfDetailsIntent
    data object HideDeleteConfirmationDialog : ShelfDetailsIntent
    data object ConfirmDeleteShelf : ShelfDetailsIntent
    data class ShowRemoveGameConfirmationDialog(val game: GameUiModel) : ShelfDetailsIntent
    data object HideRemoveGameConfirmationDialog : ShelfDetailsIntent
    data object ConfirmRemoveGameFromShelf : ShelfDetailsIntent
    data class RemoveGameFromShelf(val game: GameUiModel) : ShelfDetailsIntent
}
