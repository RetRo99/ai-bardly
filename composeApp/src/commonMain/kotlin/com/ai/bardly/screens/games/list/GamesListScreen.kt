package com.ai.bardly.screens.games.list

import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import app.cash.paging.PagingData
import app.cash.paging.compose.collectAsLazyPagingItems
import app.cash.paging.compose.itemKey
import com.ai.bardly.GameUiModel
import com.ai.bardly.base.BaseViewState
import com.ai.bardly.ui.GameCard
import kotlinx.coroutines.flow.Flow
import org.koin.compose.viewmodel.koinViewModel

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun SharedTransitionScope.GamesListScreen(
    animatedVisibilityScope: AnimatedVisibilityScope,
) {
    val viewModel = koinViewModel<GamesListViewModel>()
    val viewState = viewModel.viewState.collectAsState()
    GamesScreenContent(
        state = viewState,
        animatedVisibilityScope = animatedVisibilityScope,
        onGameClicked = viewModel::onGameClicked,
        onOpenChatClicked = viewModel::onOpenChatClicked,
    )
}

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
private fun SharedTransitionScope.GamesScreenContent(
    state: State<BaseViewState<GamesListViewState>>,
    animatedVisibilityScope: AnimatedVisibilityScope,
    onGameClicked: (GameUiModel) -> Unit,
    onOpenChatClicked: (String, Int) -> Unit
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
                animatedVisibilityScope = animatedVisibilityScope,
                onGameClicked = onGameClicked,
                onOpenChatClicked = onOpenChatClicked,
            )
        }
    }
}

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
private fun SharedTransitionScope.GamesList(
    games: Flow<PagingData<GameUiModel>>,
    animatedVisibilityScope: AnimatedVisibilityScope,
    onGameClicked: (GameUiModel) -> Unit,
    onOpenChatClicked: (String, Int) -> Unit
) {
    val items = games.collectAsLazyPagingItems()
    val state = rememberLazyGridState()
    val density = LocalDensity.current
    LazyVerticalGrid(
        state = state,
        columns = GridCells.Fixed(2), // Ensures 2 cards per row
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        contentPadding = PaddingValues(8.dp)
    ) {
        items(
            count = items.itemCount,
            key = items.itemKey { it.id },
        ) { index ->
            val game = items[index]
            if (game != null) {
                val row = state.layoutInfo.visibleItemsInfo.find { it.index == index }?.row
                val itemsInRow = state.layoutInfo.visibleItemsInfo.filter { it.row == row }
                val maxHeightInRow = itemsInRow.maxOfOrNull { it.size.height }
                val maxHeightInRowDp = with(density) { maxHeightInRow?.toDp() } ?: Dp.Unspecified
                GameCard(
                    game = game,
                    onGameClicked = onGameClicked,
                    animatedVisibilityScope = animatedVisibilityScope,
                    modifier = Modifier.height(maxHeightInRowDp),
                    onOpenChatClicked = onOpenChatClicked
                )
            }
        }
    }
}
