package com.bardly.home.ui

import com.bardly.games.ui.model.GameUiModel

data class HomeViewState(
    val recentGames: List<GameUiModel> = emptyList(),
)