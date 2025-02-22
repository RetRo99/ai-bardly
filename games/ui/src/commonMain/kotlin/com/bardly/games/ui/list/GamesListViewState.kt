package com.bardly.games.ui.list

import androidx.paging.PagingData
import com.bardly.games.ui.model.GameUiModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

data class GamesListViewState(
    val games: Flow<PagingData<GameUiModel>> = flowOf(PagingData.empty()),
    val isSearchActive: Boolean = false,
    val query: String = "",
    val searchResults: Flow<PagingData<GameUiModel>> = flowOf(PagingData.empty()),
)
