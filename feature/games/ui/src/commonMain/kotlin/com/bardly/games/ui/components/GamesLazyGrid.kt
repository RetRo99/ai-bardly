package com.bardly.games.ui.components

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.bardly.games.ui.model.GameUiModel

@Composable
fun GamesLazyGrid(
    itemCount: () -> Int,
    getItem: (Int) -> GameUiModel?,
    getKey: (Int) -> Any,
    onGameClicked: (GameUiModel) -> Unit,
    onOpenChatClicked: (String, Int) -> Unit,
    contentPadding: PaddingValues = PaddingValues(16.dp),
    gridState: LazyGridState = rememberLazyGridState(),
    emptyStateContent: @Composable () -> Unit = {},
) {
    val density = LocalDensity.current
    val count = itemCount()

    if (count == 0) {
        emptyStateContent()
    } else {
        LazyVerticalGrid(
            state = gridState,
            columns = GridCells.Fixed(2),
            modifier = Modifier.fillMaxSize(),
            contentPadding = contentPadding,
        ) {
            items(
                count = count,
                key = getKey,
                contentType = { "GameCard" }
            ) { index ->
                val game = getItem(index)
                if (game != null) {
                    val row = gridState.layoutInfo.visibleItemsInfo.find { it.index == index }?.row
                    val itemsInRow = gridState.layoutInfo.visibleItemsInfo.filter { it.row == row }
                    val maxHeightInRow = itemsInRow.maxOfOrNull { it.size.height }
                    val maxHeightInRowDp =
                        with(density) { maxHeightInRow?.toDp() } ?: Dp.Unspecified
                    GameCard(
                        game = game,
                        onClick = onGameClicked,
                        modifier = Modifier.height(maxHeightInRowDp).animateItem().animateContentSize(),
                        onChatClick = onOpenChatClicked
                    )
                }
            }
        }
    }
}
