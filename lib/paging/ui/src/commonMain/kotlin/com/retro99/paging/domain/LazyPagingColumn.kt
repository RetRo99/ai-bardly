package com.retro99.paging.domain

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyItemScope
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState

@Composable
fun <T : PagingItem> LazyPagingColumn(
    lazyItems: LazyPagingItems<T>,
    modifier: Modifier = Modifier,
    state: LazyListState = rememberLazyListState(),
    key: ((index: Int) -> Any)? = { index -> lazyItems.peek(index)?.id ?: index },
    contentType: (index: Int) -> Any? = { null },
    nextPageLoadContent: @Composable LazyItemScope.() -> Unit = {},
    nextPageLoadErrorContent: @Composable LazyItemScope.() -> Unit = {},
    initialPageLoadContent: @Composable LazyItemScope.() -> Unit = {},
    initialPageErrorContent: @Composable LazyItemScope.() -> Unit = {},
    emptyStateContent: @Composable LazyItemScope.() -> Unit = {},
    contentPadding: PaddingValues = PaddingValues(0.dp),
    reverseLayout: Boolean = false,
    verticalArrangement: Arrangement.Vertical =
        if (!reverseLayout) Arrangement.Top else Arrangement.Bottom,
    refreshItems: () -> Boolean = { false },
    footer: @Composable (LazyItemScope.() -> Unit) = {},
    refreshOnResume: Boolean = true,
    lazyItemContent: @Composable LazyItemScope.(T) -> Unit,
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

    LazyColumn(
        contentPadding = contentPadding,
        state = state,
        modifier = modifier,
        reverseLayout = reverseLayout,
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