package com.ai.bardly.feature.games.ui.details

import com.ai.bardly.analytics.Analytics
import com.ai.bardly.analytics.AnalyticsEvent
import com.ai.bardly.analytics.AnalyticsEventOrigin
import com.ai.bardly.base.BaseComponentImpl
import com.ai.bardly.base.BaseViewState
import com.ai.bardly.feature.games.domain.GamesRepository
import com.ai.bardly.feature.games.ui.model.GameUiModel
import com.arkivanov.decompose.ComponentContext
import kotlinx.coroutines.launch

class DefaultGameDetailsComponent(
    componentContext: ComponentContext,
    private val game: GameUiModel,
    private val gamesRepository: GamesRepository,
    private val navigateToChat: (String, Int) -> Unit,
    private val navigateBack: () -> Unit,
    private val analytics: Analytics,
) : BaseComponentImpl<GameDetailsViewState, GameDetailsIntent>(componentContext),
    GameDetailsComponent {

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
        navigateToChat(game.title, game.id)
    }

    private fun updateGameOpen(gameId: Int) {
        scope.launch {
            gamesRepository.updateGameOpenDate(gameId)
        }
    }
}
