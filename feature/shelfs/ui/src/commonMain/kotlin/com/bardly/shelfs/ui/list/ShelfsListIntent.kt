package com.bardly.shelfs.ui.list

import com.bardly.shelfs.ui.model.ShelfUiModel
import com.retro99.base.ui.BaseScreenIntent

sealed interface ShelfsListIntent : BaseScreenIntent {
    data class ShelfClicked(val shelf: ShelfUiModel) : ShelfsListIntent
}