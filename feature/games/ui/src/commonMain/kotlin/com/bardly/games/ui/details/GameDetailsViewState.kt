package com.bardly.games.ui.details

import com.bardly.games.ui.model.GameUiModel

data class GameDetailsViewState(
    val game: GameUiModel,
    val isFavorite: Boolean? = null,
    val shelfs: List<ShelfInfo> = emptyList(),
)
