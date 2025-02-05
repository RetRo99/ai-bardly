package com.ai.bardly.feature.games.ui.details

import androidx.lifecycle.viewModelScope
import com.ai.bardly.analytics.AnalyticsEvent
import com.ai.bardly.analytics.AnalyticsEventOrigin
import com.ai.bardly.base.BaseViewModel
import com.ai.bardly.base.BaseViewState
import com.ai.bardly.feature.games.domain.GamesRepository
import com.ai.bardly.feature.games.ui.model.GameUiModel
import com.ai.bardly.navigation.GeneralDestination
import kotlinx.coroutines.launch

class GameDetailsViewModel(
    private val game: GameUiModel,
    private val gamesRepository: GamesRepository,
) : BaseViewModel<GameDetailsViewState, GameDetailsIntent>() {

    override val defaultViewState = GameDetailsViewState(game)

    override val initialState = BaseViewState.Success(defaultViewState)

    init {
        updateGameOpen(game.id)
    }

    override suspend fun handleScreenIntent(intent: GameDetailsIntent) {
        when (intent) {
            GameDetailsIntent.NavigateBack -> navigateBack()
            GameDetailsIntent.OpenChatClicked -> openChat()
        }
    }

    private fun openChat() {
        analytics.log(
            AnalyticsEvent.OpenChat(
                gameTitle = game.title,
                origin = AnalyticsEventOrigin.GameDetails,
            )
        )
        navigateTo(
            GeneralDestination.Chat(
                game.title,
                game.id
            )
        )
    }

    private fun updateGameOpen(gameId: Int) {
        viewModelScope.launch {
            gamesRepository.updateGameOpenDate(gameId)
        }
    }
}
