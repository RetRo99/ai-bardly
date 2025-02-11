package com.ai.bardly.feature.games.ui.list

import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.ai.bardly.analytics.AnalyticsEvent
import com.ai.bardly.analytics.AnalyticsEventOrigin
import com.ai.bardly.base.BaseComponent
import com.ai.bardly.base.BaseComponentImpl
import com.ai.bardly.feature.games.domain.GamesRepository
import com.ai.bardly.feature.games.ui.model.GameUiModel
import com.ai.bardly.feature.games.ui.model.toUiModels
import com.ai.bardly.navigation.GeneralDestination
import com.arkivanov.decompose.ComponentContext
import kotlinx.coroutines.Dispatchers
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

interface GamesListComponent : BaseComponent<GamesListViewState, GamesListIntent>


class DefaultGamesListComponent(
    componentContext: ComponentContext,
    private val gamesRepository: GamesRepository
) : BaseComponentImpl<GamesListViewState, GamesListIntent>(componentContext), GamesListComponent {

    override val defaultViewState = GamesListViewState()

    private val searchFlow = MutableStateFlow<String?>(null)

    init {
        loadInitialGames()
        subscribeToSearchQuery()
    }

    override suspend fun handleScreenIntent(intent: GamesListIntent) {
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
        navigateTo(GeneralDestination.GameDetail(game))
    }


    private fun onOpenChatClicked(gameTitle: String, gameId: Int) {
        analytics.log(
            AnalyticsEvent.OpenChat(
                gameTitle = gameTitle,
                origin = getEventOrigin(),
            )
        )
        navigateTo(GeneralDestination.Chat(gameTitle, gameId))
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
