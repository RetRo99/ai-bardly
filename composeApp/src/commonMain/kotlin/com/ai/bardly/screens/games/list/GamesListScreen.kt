package com.ai.bardly.screens.games.list

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.ai.bardly.GameUiModel
import com.ai.bardly.base.BaseViewState
import com.ai.bardly.ui.GameCard
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun GamesListScreen(
) {
    val viewModel = koinViewModel<GamesListViewModel>()
    val viewState = viewModel.viewState.collectAsState()
    GamesScreenContent(
        state = viewState,
        onGameClicked = viewModel::onGameClicked
    )
}

@Composable
private fun GamesScreenContent(
    state: State<BaseViewState<GamesListViewState>>,
    onGameClicked: (GameUiModel) -> Unit
) {
    when (val viewState = state.value) {
        is BaseViewState.Loading -> {
            // Loading state
        }

        is BaseViewState.Error -> {
            // Error state
        }

        is BaseViewState.Loaded -> {
            GamesList(
                games = viewState.data.games,
                onGameClicked = onGameClicked
            )
        }
    }
}

@Composable
private fun GamesList(
    games: List<GameUiModel>,
    onGameClicked: (GameUiModel) -> Unit
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2), // Ensures 2 cards per row
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        contentPadding = PaddingValues(8.dp)
    ) {
        items(games) { game ->
            GameCard(
                game,
                onGameClicked = onGameClicked,
            )
        }
    }
}
