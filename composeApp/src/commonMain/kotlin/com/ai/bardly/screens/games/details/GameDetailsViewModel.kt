package com.ai.bardly.screens.games.details

import com.ai.bardly.GameUiModel
import com.ai.bardly.base.BaseViewModel
import com.ai.bardly.base.BaseViewState

class GameDetailsViewModel(private val game: GameUiModel) :
    BaseViewModel<BaseViewState<GameDetailsViewState>>() {

    override val initialState: BaseViewState<GameDetailsViewState>
        get() = BaseViewState.Loaded(GameDetailsViewState(game))

    fun onBackClick() {
        TODO("Not yet implemented")
    }
}
