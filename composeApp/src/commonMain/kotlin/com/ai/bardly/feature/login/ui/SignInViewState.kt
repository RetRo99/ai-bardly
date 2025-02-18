package com.ai.bardly.feature.login.ui

import com.ai.bardly.feature.main.games.ui.model.GameUiModel

data class SignInViewState(
    val recentGames: List<GameUiModel> = emptyList(),
)