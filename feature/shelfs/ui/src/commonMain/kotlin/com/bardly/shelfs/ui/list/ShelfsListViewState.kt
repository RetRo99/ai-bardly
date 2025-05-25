package com.bardly.shelfs.ui.list

import com.bardly.shelfs.ui.model.ShelfUiModel

data class ShelfsListViewState(
    val shelfs: List<ShelfUiModel> = emptyList(),
    val isCreatingShelf: Boolean = false,
    val showCreateShelfDialog: Boolean = false,
    val createShelfError: String? = null,
)
