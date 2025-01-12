package com.ai.bardly.screens.games.list

import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import app.cash.paging.cachedIn
import com.ai.bardly.GameUiModel
import com.ai.bardly.base.BaseViewModel
import com.ai.bardly.base.BaseViewState
import com.ai.bardly.base.copy
import com.ai.bardly.domain.games.GamesRepository
import com.ai.bardly.navigation.GeneralDestination
import com.ai.bardly.toUiModels
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.launch

class GamesListViewModel(
    private val gamesRepository: GamesRepository
) : BaseViewModel<BaseViewState<GamesListViewState>>() {

    init {
        loadInitialGames()
    }

    fun onGameClicked(game: GameUiModel) {
        navigateTo(GeneralDestination.GameDetail(game))
    }

    fun onOpenChatClicked(gameTitle: String, gameId: Int) {
        navigateTo(GeneralDestination.ChatDetail(gameTitle, gameId))
    }

    fun onSearchQueryChanged(query: String) {
        searchGames(query)
        updateState {
            it.copy {
                it.copy(
                    query = query
                )
            }
        }
    }

    fun onSearchStateChanged(isActive: Boolean) {
        updateState {
            it.copy {
                it.copy(
                    isSearchActive = isActive,
                    searchResults = flowOf(PagingData.empty()),
                    query = if (isActive) it.query else ""
                )
            }
        }
    }

    private fun loadInitialGames(query: String? = null) {
        fetchGames(query) { items ->
            updateState {
                BaseViewState.Loaded(
                    GamesListViewState(items)
                )
            }
        }
    }

    private fun searchGames(query: String) {
        fetchGames(query) { items ->
            updateState {
                it.copy {
                    it.copy(searchResults = items)
                }
            }
        }
    }

    private fun fetchGames(
        query: String?,
        onResult: (Flow<PagingData<GameUiModel>>) -> Unit
    ) {
        viewModelScope.launch {
            gamesRepository
                .getGames(query)
                .cachedIn(viewModelScope)
                .toUiModels()
                .let(onResult)
        }
    }

}
