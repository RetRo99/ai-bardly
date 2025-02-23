package com.ai.bardly.feature.main.home.ui

import com.ai.bardly.annotations.ActivityScope
import com.arkivanov.decompose.ComponentContext
import com.bardly.games.ui.model.GameUiModel
import com.bardly.games.ui.model.toUiModel
import com.retro99.base.ui.BasePresenterImpl
import com.retro99.base.ui.BaseViewState
import com.retro99.network.implementation.GamesRepository
import kotlinx.coroutines.launch
import me.tatarka.inject.annotations.Assisted
import me.tatarka.inject.annotations.Inject
import retro99.analytics.api.Analytics
import retro99.analytics.api.AnalyticsEvent
import retro99.analytics.api.AnalyticsEventOrigin
import software.amazon.lastmile.kotlin.inject.anvil.ContributesBinding

typealias HomePresenterFactory = (
    ComponentContext,
    navigateToChat: (String, Int) -> Unit,
    navigateToGameDetails: (GameUiModel) -> Unit,
) -> DefaultHomePresenter

@Inject
@ContributesBinding(ActivityScope::class, boundType = HomePresenter::class)
class DefaultHomePresenter(
    @Assisted componentContext: ComponentContext,
    @Assisted private val navigateToChat: (String, Int) -> Unit,
    @Assisted private val navigateToGameDetails: (GameUiModel) -> Unit,
    private val gamesRepository: GamesRepository,
    private val analytics: Analytics,
) : BasePresenterImpl<HomeViewState, HomeIntent>(componentContext), HomePresenter {

    override val defaultViewState = HomeViewState()

    override val initialState = BaseViewState.Success(defaultViewState)

    override fun onResume() {
        loadRecentGames()
    }

    override fun handleScreenIntent(intent: HomeIntent) {
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
        navigateToChat(gameTitle, gameId)
    }

    private fun openGameDetails(game: GameUiModel) {
        analytics.log(
            AnalyticsEvent.OpenGameDetails(
                gameTitle = game.title,
                origin = AnalyticsEventOrigin.Home,
            )
        )
        navigateToGameDetails(game)
    }

    private fun loadRecentGames() {
        scope.launch {
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