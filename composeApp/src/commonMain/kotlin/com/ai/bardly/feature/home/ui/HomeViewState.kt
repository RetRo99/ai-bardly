package com.ai.bardly.feature.home.ui

import com.ai.bardly.feature.games.ui.model.GameUiModel

data class HomeViewState(
    val recentGames: List<GameUiModel> = emptyList(),
)