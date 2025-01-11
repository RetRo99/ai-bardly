package com.ai.bardly.screens.games.list

import androidx.lifecycle.viewModelScope
import app.cash.paging.cachedIn
import com.ai.bardly.GameUiModel
import com.ai.bardly.base.BaseViewModel
import com.ai.bardly.base.BaseViewState
import com.ai.bardly.data.GamesRepository
import com.ai.bardly.navigation.GeneralDestination
import com.ai.bardly.toUiModels
import kotlinx.coroutines.launch

class GamesListViewModel(
    private val gamesRepository: GamesRepository
) : BaseViewModel<BaseViewState<GamesListViewState>>() {

    init {
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

    fun onGameClicked(game: GameUiModel) {
        navigateTo(GeneralDestination.GameDetail(game))
    }

    fun onOpenChatClicked(gameTitle: String) {
        navigateTo(GeneralDestination.ChatDetail(gameTitle))
    }

}
