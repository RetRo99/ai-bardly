package com.bardly.games.ui.details

import com.ai.bardly.annotations.ActivityScope
import com.arkivanov.decompose.ComponentContext
import com.bardly.games.ui.model.GameUiModel
import com.github.michaelbull.result.onSuccess
import com.retro99.analytics.api.Analytics
import com.retro99.analytics.api.AnalyticsEvent
import com.retro99.analytics.api.AnalyticsEventOrigin
import com.retro99.auth.domain.manager.UserSessionManager
import com.retro99.base.ui.BasePresenterImpl
import com.retro99.base.ui.BaseViewState
import com.retro99.base.ui.compose.TextWrapper
import com.retro99.snackbar.api.SnackbarManager
import com.retro99.translations.StringRes
import com.retro99.games.domain.GamesRepository
import com.retro99.games.domain.usecase.ToggleGameFavouriteStateUseCase
import com.retro99.shelfs.domain.ShelfsRepository
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import me.tatarka.inject.annotations.Assisted
import me.tatarka.inject.annotations.Inject
import resources.translations.game_added_to_shelf
import software.amazon.lastmile.kotlin.inject.anvil.ContributesBinding

typealias GameDetailsPresenterFactory = (
    componentContext: ComponentContext,
    game: GameUiModel,
    navigateToChat: (String, String) -> Unit,
    navigateBack: () -> Unit,
    openLogin: () -> Unit,
) -> DefaultGameDetailsPresenter

@Inject
@ContributesBinding(ActivityScope::class, boundType = GameDetailsPresenter::class)
class DefaultGameDetailsPresenter(
    @Assisted componentContext: ComponentContext,
    @Assisted private val game: GameUiModel,
    @Assisted private val navigateToChat: (String, String) -> Unit,
    @Assisted private val navigateBack: () -> Unit,
    @Assisted private val openLogin: () -> Unit,
    private val gamesRepository: GamesRepository,
    private val toggleFavoritesUseCase: ToggleGameFavouriteStateUseCase,
    private val userSessionManager: UserSessionManager,
    private val analytics: Analytics,
    private val shelfsRepository: ShelfsRepository,
    private val snackbarManager: SnackbarManager,
) : BasePresenterImpl<GameDetailsViewState, GameDetailsIntent>(componentContext),
    GameDetailsPresenter {

    override val defaultViewState = GameDetailsViewState(game)

    override val initialState = BaseViewState.Success(defaultViewState)

    init {
        updateGameOpen(game.id)
        fetchIsFavoriteGame(game.id)
        fetchShelfs()
    }

    private fun fetchShelfs() {
        if (userSessionManager.isUserLoggedIn) {
            scope.launch {
                shelfsRepository.getShelfs()
                    .onEach { result ->
                        result
                            .onSuccess { shelfs ->
                            val shelfInfoList = shelfs.map { shelf ->
                                ShelfInfo(
                                    id = shelf.id,
                                    name = shelf.name
                                )
                            }
                            updateOrSetSuccess { it.copy(shelfs = shelfInfoList) }
                        }
                    }.launchIn(this)
            }
        }
    }

    private fun fetchIsFavoriteGame(gameId: String) {
        gamesRepository.isMarkedAsFavorite(gameId)
            .onEach { isFavorite ->
                updateOrSetSuccess { it.copy(isFavorite = isFavorite) }
            }.launchIn(scope)
    }

    override fun handleScreenIntent(intent: GameDetailsIntent) {
        when (intent) {
            GameDetailsIntent.NavigateBack -> navigateBack()
            GameDetailsIntent.OpenChatClicked -> openChat()
            is GameDetailsIntent.OnChangeFavoriteClicked -> onChangeFavoriteClicked(intent.isFavoriteNew)
            is GameDetailsIntent.AddGameToShelf -> addGameToSelectedShelf(intent.shelfId)
            GameDetailsIntent.ShowShelfSelectionDialog -> showShelfSelectionDialog()
            GameDetailsIntent.HideShelfSelectionDialog -> hideShelfSelectionDialog()
        }
    }

    private fun addGameToShelf(shelfId: String) {
        if (userSessionManager.isUserLoggedIn) {
            // Get the current state to find the shelf name
            val currentState = viewState.value as? BaseViewState.Success<GameDetailsViewState>
            val shelfName = currentState?.data?.shelfs?.find { it.id == shelfId }?.name ?: "Unknown shelf"

            launchDataOperation(
                block = { shelfsRepository.addGameToShelf(shelfId, game.id) },
            ) {
                // Show snackbar notification when game is added to shelf with the shelf name
                snackbarManager.showSnackbar(
                    TextWrapper.Resource(StringRes.game_added_to_shelf, shelfName)
                )
            }
        } else {
            openLogin()
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

    private fun addGameToSelectedShelf(shelfId: String) {
        addGameToShelf(shelfId)
        updateOrSetSuccess { it.copy(isShelfSelectionDialogVisible = false) }
    }

    private fun showShelfSelectionDialog() {
        if (userSessionManager.isUserLoggedIn) {
            updateOrSetSuccess { it.copy(isShelfSelectionDialogVisible = true) }
        } else {
            openLogin()
        }
    }

    private fun hideShelfSelectionDialog() {
        updateOrSetSuccess { it.copy(isShelfSelectionDialogVisible = false) }
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

    private fun updateGameOpen(gameId: String) {
        scope.launch {
            gamesRepository.updateGameOpenDate(gameId)
        }
    }
}
