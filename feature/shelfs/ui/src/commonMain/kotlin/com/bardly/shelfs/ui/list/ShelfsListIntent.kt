package com.bardly.shelfs.ui.list

import com.bardly.games.ui.model.GameUiModel
import com.bardly.shelfs.ui.model.ShelfUiModel
import com.retro99.base.ui.BaseScreenIntent

sealed interface ShelfsListIntent : BaseScreenIntent {
    data class ShelfClicked(val shelf: ShelfUiModel) : ShelfsListIntent
    data class GameClicked(val game: GameUiModel) : ShelfsListIntent
    data class CreateShelf(val name: String, val description: String? = null) : ShelfsListIntent
    data object ShowCreateShelfDialog : ShelfsListIntent
    data object HideCreateShelfDialog : ShelfsListIntent
}
