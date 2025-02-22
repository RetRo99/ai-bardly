package com.ai.bardly.feature.main.home.ui

import com.bardly.games.ui.model.GameUiModel

data class HomeViewState(
    val recentGames: List<GameUiModel> = emptyList(),
)