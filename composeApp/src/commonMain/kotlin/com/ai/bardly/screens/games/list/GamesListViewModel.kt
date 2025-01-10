package com.ai.bardly.screens.games.list

import androidx.lifecycle.viewModelScope
import com.ai.bardly.GameUiModel
import com.ai.bardly.base.BaseViewModel
import com.ai.bardly.base.BaseViewState
import com.ai.bardly.data.GamesRepository
import com.ai.bardly.navigation.GeneralDestination
import com.ai.bardly.toUiModels
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class GamesListViewModel(
    private val gamesRepository: GamesRepository
) : BaseViewModel<BaseViewState<GamesListViewState>>() {

    init {
        gamesRepository.getObjects()
            .onEach { games ->
                updateState {
                    BaseViewState.Loaded(GamesListViewState(games.toUiModels()))
                }
            }
            .launchIn(viewModelScope)
    }

    fun onGameClicked(game: GameUiModel) {
        navigate(GeneralDestination.GameDetail(game))
    }

}
