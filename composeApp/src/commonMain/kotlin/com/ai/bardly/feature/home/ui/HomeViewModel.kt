package com.ai.bardly.feature.home.ui

import androidx.lifecycle.viewModelScope
import com.ai.bardly.analytics.AnalyticsEvent
import com.ai.bardly.analytics.AnalyticsEventOrigin
import com.ai.bardly.base.BaseViewModel
import com.ai.bardly.base.BaseViewState
import com.ai.bardly.feature.games.domain.GamesRepository
import com.ai.bardly.feature.games.ui.model.GameUiModel
import com.ai.bardly.feature.games.ui.model.toUiModel
import com.ai.bardly.navigation.GeneralDestination
import kotlinx.coroutines.launch
import org.koin.android.annotation.KoinViewModel

@KoinViewModel
class HomeViewModel(
    private val gamesRepository: GamesRepository,
) : BaseViewModel<HomeViewState, HomeIntent>() {

    override val defaultViewState = HomeViewState()

    override val initialState = BaseViewState.Success(defaultViewState)

    override fun onScreenDisplayed() {
        loadRecentGames()
    }

    override suspend fun handleScreenIntent(intent: HomeIntent) {
        when (intent) {
            is HomeIntent.OpenChatClicked -> openChat(intent.gameTitle, intent.gameId)
            is HomeIntent.OpenGameDetails -> openGameDetails(intent.game)
        }
    }

    private fun openChat(gameTitle: String, gameId: Int) {
        analytics.log(
            AnalyticsEvent.OpenChat(
                gameTitle = gameTitle,
                origin = AnalyticsEventOrigin.Home,
            )
        )
        navigateTo(GeneralDestination.Chat(gameTitle, gameId))
    }

    private fun openGameDetails(game: GameUiModel) {
        analytics.log(
            AnalyticsEvent.OpenGameDetails(
                gameTitle = game.title,
                origin = AnalyticsEventOrigin.Home,
            )
        )
        navigateTo(GeneralDestination.GameDetail(game))
    }

    private fun loadRecentGames() {
        viewModelScope.launch {
            gamesRepository.getRecentlyOpenGames()
                .onSuccess { games ->
                    updateOrSetSuccess {
                        it.copy(
                            recentGames = games.toUiModel()
                        )
                    }
                }
                .onFailure { error ->
                    setError(throwable = error)
                }
        }
    }
}