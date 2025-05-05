package com.retro99.paging.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.paging.LoadState
import com.retro99.paging.domain.PagingItem

/**
 * Common utility functions for LazyPagingColumn and LazyPagingGrid
 */

/**
 * Handles the refresh logic for paging components
 */
@Composable
fun HandlePagingRefresh(
    lazyItems: LazyPagingItems<*>,
    refreshItems: () -> Boolean,
    refreshOnResume: Boolean
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
}

/**
 * Determines if the empty state content should be shown
 */
fun shouldShowEmptyState(lazyItems: LazyPagingItems<*>): Boolean {
    return lazyItems.itemCount == 0 && lazyItems.loadState.refresh is LoadState.NotLoading
}

/**
 * Determines if the initial page loading content should be shown
 */
fun shouldShowInitialLoading(lazyItems: LazyPagingItems<*>): Boolean {
    return lazyItems.itemCount == 0 && lazyItems.loadState.refresh is LoadState.Loading
}

/**
 * Determines if the initial page error content should be shown
 */
fun shouldShowInitialError(lazyItems: LazyPagingItems<*>): Boolean {
    return lazyItems.loadState.refresh is LoadState.Error
}

/**
 * Determines if the next page loading content should be shown
 */
fun shouldShowNextPageLoading(lazyItems: LazyPagingItems<*>): Boolean {
    return lazyItems.loadState.append is LoadState.Loading
}

/**
 * Determines if the next page error content should be shown
 */
fun shouldShowNextPageError(lazyItems: LazyPagingItems<*>): Boolean {
    return lazyItems.loadState.append is LoadState.Error
}

/**
 * Determines if the items should be shown
 */
fun shouldShowItems(lazyItems: LazyPagingItems<*>): Boolean {
    return lazyItems.itemCount != 0
}

/**
 * Common function to handle all conditional content items for LazyPagingColumn and LazyPagingGrid.
 * This function checks various conditions and adds the appropriate content items.
 *
 * @param lazyItems The LazyPagingItems to check conditions against
 * @param reverseLayout Whether the layout is reversed
 * @param emptyStateContent Content to show when there are no items and not loading
 * @param initialPageLoadContent Content to show during initial page loading
 * @param initialPageErrorContent Content to show when initial page loading fails
 * @param nextPageLoadContent Content to show during next page loading
 * @param nextPageLoadErrorContent Content to show when next page loading fails
 * @param footer Footer content
 * @param itemContent Function to render each item
 * @param addItem Function to add an item to the lazy layout
 * @param addItems Function to add multiple items to the lazy layout
 */
inline fun <T : PagingItem, S> handlePagingItems(
    lazyItems: LazyPagingItems<T>,
    reverseLayout: Boolean,
    noinline emptyStateContent: @Composable S.() -> Unit,
    noinline initialPageLoadContent: @Composable S.() -> Unit,
    noinline initialPageErrorContent: @Composable S.() -> Unit,
    noinline nextPageLoadContent: @Composable S.() -> Unit,
    noinline nextPageLoadErrorContent: @Composable S.() -> Unit,
    noinline footer: @Composable S.() -> Unit,
    noinline key: ((index: Int) -> Any)?,
    noinline contentType: (index: Int) -> Any?,
    noinline itemContent: @Composable S.(T) -> Unit,
    addItem: (content: @Composable S.() -> Unit) -> Unit,
    addItems: (count: Int, key: ((index: Int) -> Any)?, contentType: (index: Int) -> Any?, itemContent: @Composable S.(index: Int) -> Unit) -> Unit
) {
    if (reverseLayout) {
        addItem { footer() }
    }

    if (shouldShowEmptyState(lazyItems)) {
        addItem { emptyStateContent() }
    }

    if (shouldShowInitialLoading(lazyItems)) {
        addItem { initialPageLoadContent() }
    }

    if (shouldShowInitialError(lazyItems)) {
        addItem { initialPageErrorContent() }
    }

    if (shouldShowItems(lazyItems)) {
        addItems(
            lazyItems.itemCount,
            key,
            contentType
        ) { index ->
            val lazyItem = lazyItems[index] ?: return@addItems
            itemContent(lazyItem)
        }
    }

    if (shouldShowNextPageLoading(lazyItems)) {
        addItem { nextPageLoadContent() }
    }

    if (shouldShowNextPageError(lazyItems)) {
        addItem { nextPageLoadErrorContent() }
    }

    if (!reverseLayout) {
        addItem { footer() }
    }
}
