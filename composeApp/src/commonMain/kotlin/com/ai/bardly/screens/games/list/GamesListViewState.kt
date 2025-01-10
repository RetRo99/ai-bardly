package com.ai.bardly.screens.games.list

import app.cash.paging.PagingData
import com.ai.bardly.GameUiModel
import kotlinx.coroutines.flow.Flow

data class GamesListViewState(
    val games: Flow<PagingData<GameUiModel>>,
)
