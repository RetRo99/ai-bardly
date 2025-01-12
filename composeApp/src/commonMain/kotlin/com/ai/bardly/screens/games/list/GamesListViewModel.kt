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
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.launch

class GamesListViewModel(
    private val gamesRepository: GamesRepository
) : BaseViewModel<BaseViewState<GamesListViewState>>() {

    init {
        loadGames()
    }

    fun onGameClicked(game: GameUiModel) {
        navigateTo(GeneralDestination.GameDetail(game))
    }

    fun onOpenChatClicked(gameTitle: String, gameId: Int) {
        navigateTo(GeneralDestination.ChatDetail(gameTitle, gameId))
    }

    fun onSearchQueryChanged(query: String) {
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

    private fun loadGames() {
        viewModelScope.launch {
            val items = gamesRepository
                .getObjects()
                .cachedIn(viewModelScope)
                .toUiModels()
            updateState {
                BaseViewState.Loaded(
                    GamesListViewState(
                        items
                    )
                )
            }
        }
    }

}
