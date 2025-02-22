package com.ai.bardly.feature.main.games.ui.details

import com.ai.bardly.analytics.Analytics
import com.ai.bardly.analytics.AnalyticsEvent
import com.ai.bardly.analytics.AnalyticsEventOrigin
import com.ai.bardly.annotations.ActivityScope
import com.ai.bardly.feature.main.games.domain.GamesRepository
import com.ai.bardly.feature.main.games.ui.model.GameUiModel
import com.arkivanov.decompose.ComponentContext
import com.retro99.base.BasePresenterImpl
import com.retro99.base.BaseViewState
import kotlinx.coroutines.launch
import me.tatarka.inject.annotations.Assisted
import me.tatarka.inject.annotations.Inject
import software.amazon.lastmile.kotlin.inject.anvil.ContributesBinding

typealias GameDetailsPresenterFactory = (
    componentContext: ComponentContext,
    game: GameUiModel,
    navigateToChat: (String, Int) -> Unit,
    navigateBack: () -> Unit,
) -> DefaultGameDetailsPresenter

@Inject
@ContributesBinding(ActivityScope::class, boundType = GameDetailsPresenter::class)
class DefaultGameDetailsPresenter(
    @Assisted componentContext: ComponentContext,
    @Assisted private val game: GameUiModel,
    @Assisted private val navigateToChat: (String, Int) -> Unit,
    @Assisted private val navigateBack: () -> Unit,
    private val gamesRepository: GamesRepository,
    private val analytics: Analytics,
) : BasePresenterImpl<GameDetailsViewState, GameDetailsIntent>(componentContext),
    GameDetailsPresenter {

    override val defaultViewState = GameDetailsViewState(game)

    override val initialState = BaseViewState.Success(defaultViewState)

    init {
        updateGameOpen(game.id)
    }

    override fun handleScreenIntent(intent: GameDetailsIntent) {
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
