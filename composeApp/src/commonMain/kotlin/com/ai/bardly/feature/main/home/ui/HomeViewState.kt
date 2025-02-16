package com.ai.bardly.feature.main.home.ui

import com.ai.bardly.feature.main.games.ui.model.GameUiModel

data class HomeViewState(
    val recentGames: List<GameUiModel> = emptyList(),
)