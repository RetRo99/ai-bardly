package com.bardly.games.ui.details

import com.ai.bardly.annotations.ActivityScope
import com.arkivanov.decompose.ComponentContext
import com.bardly.games.ui.model.GameUiModel
import com.retro99.auth.domain.manager.UserSessionManager
import com.retro99.base.ui.BasePresenterImpl
import com.retro99.base.ui.BaseViewState
import com.retro99.games.domain.GamesRepository
import com.retro99.games.domain.usecase.ToggleGameFavouriteStateUseCase
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import me.tatarka.inject.annotations.Assisted
import me.tatarka.inject.annotations.Inject
import retro99.analytics.api.Analytics
import retro99.analytics.api.AnalyticsEvent
import retro99.analytics.api.AnalyticsEventOrigin
import software.amazon.lastmile.kotlin.inject.anvil.ContributesBinding

typealias GameDetailsPresenterFactory = (
    componentContext: ComponentContext,
    game: GameUiModel,
    navigateToChat: (String, Int) -> Unit,
    navigateBack: () -> Unit,
    openLogin: () -> Unit,
) -> DefaultGameDetailsPresenter

@Inject
@ContributesBinding(ActivityScope::class, boundType = GameDetailsPresenter::class)
class DefaultGameDetailsPresenter(
    @Assisted componentContext: ComponentContext,
    @Assisted private val game: GameUiModel,
    @Assisted private val navigateToChat: (String, Int) -> Unit,
    @Assisted private val navigateBack: () -> Unit,
    @Assisted private val openLogin: () -> Unit,
    private val gamesRepository: GamesRepository,
    private val toggleFavoritesUseCase: ToggleGameFavouriteStateUseCase,
    private val userSessionManager: UserSessionManager,
    private val analytics: Analytics,
) : BasePresenterImpl<GameDetailsViewState, GameDetailsIntent>(componentContext),
    GameDetailsPresenter {

    override val defaultViewState = GameDetailsViewState(game)

    override val initialState = BaseViewState.Success(defaultViewState)

    init {
        updateGameOpen(game.id)
        fetchIsFavoriteGame(game.id)
    }

    private fun fetchIsFavoriteGame(gameId: Int) {
        gamesRepository.isMarkedAsFavorite(gameId)
            .onEach { isFavorite ->
                updateOrSetSuccess { it.copy(isFavorite = isFavorite) }
            }.launchIn(scope)
    }

    override fun handleScreenIntent(intent: GameDetailsIntent) {
        when (intent) {
            GameDetailsIntent.NavigateBack -> navigateBack()
            GameDetailsIntent.OpenChatClicked -> openChat()
            is GameDetailsIntent.OnChangeFavoriteClicked -> {
                onChangeFavoriteClicked(intent.isFavoriteNew)
            }
        }
    }

    private fun onChangeFavoriteClicked(isFavorite: Boolean) {
        if (userSessionManager.isUserLoggedIn) {
            launchDataOperation(
                block = { toggleFavoritesUseCase(game.id, isFavorite) },
            ) {
                updateOrSetSuccess { it.copy(isFavorite = isFavorite) }
            }
        } else {
            openLogin()
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
