package com.ai.bardly.screens.games.list

import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.ai.bardly.GameUiModel
import com.ai.bardly.base.BaseViewModel
import com.ai.bardly.base.BaseViewState
import com.ai.bardly.base.copy
import com.ai.bardly.domain.games.GamesRepository
import com.ai.bardly.navigation.GeneralDestination
import com.ai.bardly.toUiModels
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class GamesListViewModel(
    private val gamesRepository: GamesRepository
) : BaseViewModel<BaseViewState<GamesListViewState>>() {

    private val searchFlow = MutableStateFlow<String?>(null)

    init {
        loadInitialGames()
        subscribeToSearchQuery()
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
        searchFlow.update { query }
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

    private fun subscribeToSearchQuery() {
        searchFlow
            .filterNotNull()
            .debounce(300)
            .distinctUntilChanged()
            .onEach { query ->
                searchGames(query)
            }
            .flowOn(Dispatchers.IO)
            .launchIn(viewModelScope)
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
