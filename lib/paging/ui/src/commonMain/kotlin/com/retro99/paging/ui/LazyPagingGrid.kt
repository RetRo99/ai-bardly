package com.retro99.paging.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyGridItemScope
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.runtime.Composable
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
    HandlePagingRefresh(
        lazyItems = lazyItems,
        refreshItems = refreshItems,
        refreshOnResume = refreshOnResume
    )

    LazyVerticalGrid(
        columns = columns,
        contentPadding = contentPadding,
        state = state,
        modifier = modifier,
        reverseLayout = reverseLayout,
        horizontalArrangement = horizontalArrangement,
        verticalArrangement = verticalArrangement,
    ) {
        handlePagingItems(
            lazyItems = lazyItems,
            reverseLayout = reverseLayout,
            emptyStateContent = emptyStateContent,
            initialPageLoadContent = initialPageLoadContent,
            initialPageErrorContent = initialPageErrorContent,
            nextPageLoadContent = nextPageLoadContent,
            nextPageLoadErrorContent = nextPageLoadErrorContent,
            footer = footer,
            key = key,
            contentType = contentType,
            itemContent = lazyItemContent,
            addItem = { content -> item { content() } },
            addItems = { count, itemKey, itemContentType, itemContent ->
                items(
                    count = count,
                    key = itemKey,
                    contentType = itemContentType,
                ) { index -> itemContent(index) }
            }
        )
    }
}
