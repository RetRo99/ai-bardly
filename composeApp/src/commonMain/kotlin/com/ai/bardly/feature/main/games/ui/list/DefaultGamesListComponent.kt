package com.ai.bardly.feature.main.games.ui.list

import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.ai.bardly.analytics.Analytics
import com.ai.bardly.analytics.AnalyticsEvent
import com.ai.bardly.analytics.AnalyticsEventOrigin
import com.ai.bardly.annotations.ActivityScope
import com.ai.bardly.base.BasePresenterImpl
import com.ai.bardly.feature.main.games.domain.GamesRepository
import com.ai.bardly.feature.main.games.ui.model.GameUiModel
import com.ai.bardly.feature.main.games.ui.model.toUiModels
import com.arkivanov.decompose.ComponentContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import me.tatarka.inject.annotations.Assisted
import me.tatarka.inject.annotations.Inject
import software.amazon.lastmile.kotlin.inject.anvil.ContributesBinding

typealias GamesListComponentFactory = (
    ComponentContext,
    navigateToChat: (String, Int) -> Unit,
    navigateToGameDetails: (GameUiModel) -> Unit,
) -> DefaultGamesListComponent

@Inject
@ContributesBinding(ActivityScope::class, boundType = GamesListComponent::class)
class DefaultGamesListComponent(
    @Assisted componentContext: ComponentContext,
    @Assisted val navigateToChat: (String, Int) -> Unit,
    @Assisted private val navigateToGameDetails: (GameUiModel) -> Unit,
    private val gamesRepository: GamesRepository,
    private val analytics: Analytics,
) : BasePresenterImpl<GamesListViewState, GamesListIntent>(componentContext), GamesListComponent {

    override val defaultViewState = GamesListViewState()

    private val searchFlow = MutableStateFlow<String?>(null)

    init {
        loadInitialGames()
        subscribeToSearchQuery()
    }

    override fun handleScreenIntent(intent: GamesListIntent) {
        when (intent) {
            is GamesListIntent.GameClicked -> openGameDetails(intent.game)
            is GamesListIntent.OpenChatClicked -> onOpenChatClicked(intent.gameTitle, intent.gameId)
            is GamesListIntent.SearchQueryChanged -> onSearchQueryChanged(intent.query)
            is GamesListIntent.SearchStateChanged -> onSearchStateChanged(intent.isActive)
        }
    }

    private fun openGameDetails(game: GameUiModel) {
        analytics.log(
            AnalyticsEvent.OpenGameDetails(
                gameTitle = game.title,
                origin = getEventOrigin(),
            )
        )
        navigateToGameDetails(game)
    }


    private fun onOpenChatClicked(gameTitle: String, gameId: Int) {
        analytics.log(
            AnalyticsEvent.OpenChat(
                gameTitle = gameTitle,
                origin = getEventOrigin(),
            )
        )
        navigateToChat(gameTitle, gameId)
    }

    private fun onSearchQueryChanged(query: String) {
        updateOrSetSuccess {
            it.copy(
                query = query
            )
        }
        searchFlow.update { query }
    }

    private fun onSearchStateChanged(isActive: Boolean) {
        updateOrSetSuccess {
            it.copy(
                isSearchActive = isActive,
                searchResults = flowOf(PagingData.empty()),
                query = if (isActive) it.query else ""
            )
        }
    }

    @OptIn(FlowPreview::class)
    private fun subscribeToSearchQuery() {
        searchFlow
            .filterNotNull()
            .debounce(300)
            .distinctUntilChanged()
            .onEach { query ->
                searchGames(query)
            }
            .flowOn(Dispatchers.IO)
            .launchIn(scope)
    }

    private fun loadInitialGames(query: String? = null) {
        fetchGames(query) { items ->
            updateOrSetSuccess {
                it.copy(games = items)
            }
        }
    }

    private fun searchGames(query: String) {
        fetchGames(query) { items ->
            updateOrSetSuccess {
                it.copy(searchResults = items)
            }
        }
    }

    private fun fetchGames(
        query: String?,
        onResult: (Flow<PagingData<GameUiModel>>) -> Unit
    ) {
        scope.launch {
            gamesRepository
                .getGames(query)
                .cachedIn(scope)
                .toUiModels()
                .let(onResult)
        }
    }

    private fun getEventOrigin(): AnalyticsEventOrigin {
        return if (currentViewState().isSearchActive) {
            AnalyticsEventOrigin.GameSearch
        } else {
            AnalyticsEventOrigin.GameList
        }
    }
}
