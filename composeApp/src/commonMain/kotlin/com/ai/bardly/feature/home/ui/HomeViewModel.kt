package com.ai.bardly.feature.home.ui

import androidx.lifecycle.viewModelScope
import com.ai.bardly.base.BaseViewModel
import com.ai.bardly.base.BaseViewState
import com.ai.bardly.feature.games.domain.GamesRepository
import com.ai.bardly.feature.games.ui.model.GameUiModel
import com.ai.bardly.feature.games.ui.model.toUiModel
import com.ai.bardly.navigation.GeneralDestination
import kotlinx.coroutines.launch

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
        navigateTo(GeneralDestination.ChatDetail(gameTitle, gameId))
    }

    private fun openGameDetails(game: GameUiModel) {
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