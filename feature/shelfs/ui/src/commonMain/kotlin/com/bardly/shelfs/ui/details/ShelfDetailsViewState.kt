package com.bardly.shelfs.ui.details

import com.bardly.shelfs.ui.model.ShelfUiModel

data class ShelfDetailsViewState(
    val shelf: ShelfUiModel,
    val isDeleteConfirmationDialogVisible: Boolean = false,
)
