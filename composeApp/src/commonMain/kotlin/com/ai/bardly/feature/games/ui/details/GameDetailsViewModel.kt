package com.ai.bardly.feature.games.ui.details

import com.ai.bardly.GameUiModel
import com.ai.bardly.base.BaseViewModel
import com.ai.bardly.base.BaseViewState

class GameDetailsViewModel(
    private val game: GameUiModel,
) : BaseViewModel<GameDetailsViewState>() {

    override val defaultScreenData = GameDetailsViewState(game)
    override val initialState = BaseViewState.Success(defaultScreenData)

    fun onBackClick() {
        navigateBack()
    }
}
