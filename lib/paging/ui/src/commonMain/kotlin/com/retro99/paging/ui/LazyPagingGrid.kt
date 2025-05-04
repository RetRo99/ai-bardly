package com.retro99.paging.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyGridItemScope
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import com.retro99.paging.domain.PagingItem

@Composable
fun <T : PagingItem> LazyPagingGrid(
    lazyItems: LazyPagingItems<T>,
    columns: GridCells,
    modifier: Modifier = Modifier,
    state: LazyGridState = rememberLazyGridState(),
    key: ((index: Int) -> Any)? = { index -> lazyItems.peek(index)?.id ?: index },
    contentType: (index: Int) -> Any? = { null },
    nextPageLoadContent: @Composable LazyGridItemScope.() -> Unit = {},
    nextPageLoadErrorContent: @Composable LazyGridItemScope.() -> Unit = {},
    initialPageLoadContent: @Composable LazyGridItemScope.() -> Unit = {},
    initialPageErrorContent: @Composable LazyGridItemScope.() -> Unit = {},
    emptyStateContent: @Composable LazyGridItemScope.() -> Unit = {},
    contentPadding: PaddingValues = PaddingValues(0.dp),
    reverseLayout: Boolean = false,
    horizontalArrangement: Arrangement.Horizontal = Arrangement.Start,
    verticalArrangement: Arrangement.Vertical =
        if (!reverseLayout) Arrangement.Top else Arrangement.Bottom,
    refreshItems: () -> Boolean = { false },
    footer: @Composable (LazyGridItemScope.() -> Unit) = {},
    refreshOnResume: Boolean = true,
    lazyItemContent: @Composable LazyGridItemScope.(T) -> Unit,
) {
    // Use Unit as key to ensure this effect runs only once on composition
    LaunchedEffect(Unit) {
        if (refreshItems()) {
            lazyItems.refresh()
        }
    }

    // Handle refresh on resume if enabled
    if (refreshOnResume) {
        // Use a random value as the key to ensure it runs every time the composable is recomposed
        var refreshKey by remember { mutableStateOf(0) }
        LaunchedEffect(refreshKey) {
            lazyItems.refresh()
        }
        // Increment the key each time the composable is recomposed
        LaunchedEffect(Unit) {
            refreshKey++
        }
    }

    LazyVerticalGrid(
        columns = columns,
        contentPadding = contentPadding,
        state = state,
        modifier = modifier,
        reverseLayout = reverseLayout,
        horizontalArrangement = horizontalArrangement,
        verticalArrangement = verticalArrangement,
    ) {
        if (reverseLayout) {
            item {
                footer()
            }
        }

        if (lazyItems.itemCount == 0 && lazyItems.loadState.refresh is LoadState.NotLoading) {
            item {
                emptyStateContent()
            }
        }

        if (lazyItems.itemCount == 0 && lazyItems.loadState.refresh is LoadState.Loading) {
            item {
                initialPageLoadContent()
            }
        }

        if (lazyItems.loadState.refresh is LoadState.Error) {
            item {
                initialPageErrorContent()
            }
        }

        if (lazyItems.itemCount != 0) {
            items(
                count = lazyItems.itemCount,
                key = key,
                contentType = contentType,
            ) { index ->
                val lazyItem = lazyItems[index] ?: return@items
                lazyItemContent(lazyItem)
            }
        }

        if (lazyItems.loadState.append is LoadState.Loading) {
            item {
                nextPageLoadContent()
            }
        }

        if (lazyItems.loadState.append is LoadState.Error) {
            item {
                nextPageLoadErrorContent()
            }
        }

        if (!reverseLayout) {
            item {
                footer()
            }
        }
    }
}