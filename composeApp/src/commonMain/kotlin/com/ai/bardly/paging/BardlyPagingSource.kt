package com.ai.bardly.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.ai.bardly.analytics.Analytics
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class BardlyPagingSource<ITEM : PagingItem>(
    private val initialKey: Int,
    private val getItems: suspend (Int, Int) -> PagingResult<ITEM>
) : PagingSource<Int, ITEM>(), KoinComponent {

    private val analytics by inject<Analytics>()

    override val jumpingSupported: Boolean
        get() = true

    override val keyReuseSupported: Boolean
        get() = true

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ITEM> {
        val currentKey = params.key ?: initialKey
        return try {
            val pagingResult = getItems(currentKey, params.loadSize)
            LoadResult.Page(
                data = pagingResult.items,
                prevKey = pagingResult.prevKey,
                nextKey = pagingResult.nextKey
            )
        } catch (exception: Throwable) {
            analytics.logException(exception, "Failed paging load")
            return LoadResult.Error(exception)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, ITEM>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }
}
