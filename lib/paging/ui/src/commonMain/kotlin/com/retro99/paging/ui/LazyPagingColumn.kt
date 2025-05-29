package com.retro99.paging.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyItemScope
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.retro99.paging.domain.PagingItem

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
    HandlePagingRefresh(
        lazyItems = lazyItems,
        refreshItems = refreshItems,
        refreshOnResume = refreshOnResume
    )

    LazyColumn(
        contentPadding = contentPadding,
        state = state,
        modifier = modifier,
        reverseLayout = reverseLayout,
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
