package com.ai.bardly.screens.games.list

import app.cash.paging.PagingData
import com.ai.bardly.GameUiModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

data class GamesListViewState(
    val games: Flow<PagingData<GameUiModel>>,
    val isSearchActive: Boolean = false,
    val query: String = "",
    val searchResults: Flow<PagingData<GameUiModel>> = flowOf(PagingData.empty())
)
