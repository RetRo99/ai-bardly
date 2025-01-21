package com.ai.bardly.feature.games.ui.details

import com.ai.bardly.base.BaseViewModel
import com.ai.bardly.base.BaseViewState
import com.ai.bardly.feature.games.ui.model.GameUiModel

class GameDetailsViewModel(
    private val game: GameUiModel,
) : BaseViewModel<GameDetailsViewState, GameDetailsIntent>() {

    override val defaultViewState = GameDetailsViewState(game)

    override val initialState = BaseViewState.Success(defaultViewState)

    override suspend fun handleScreenIntent(intent: GameDetailsIntent) {
        TODO("Not yet implemented")
    }

}
