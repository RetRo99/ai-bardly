package com.bardly.shelfs.ui.list

import com.bardly.shelfs.ui.model.ShelfUiModel
import org.jetbrains.compose.resources.StringResource

data class ShelfsListViewState(
    val shelfs: List<ShelfUiModel> = emptyList(),
    val isCreatingShelf: Boolean = false,
    val showCreateShelfDialog: Boolean = false,
    val createShelfError: StringResource? = null,
)
