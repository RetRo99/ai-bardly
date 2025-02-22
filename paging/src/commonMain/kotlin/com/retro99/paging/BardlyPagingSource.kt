package com.retro99.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState

class BardlyPagingSource<ITEM : PagingItem>(
    private val initialKey: Int,
    private val getItems: suspend (Int, Int) -> PagingResult<ITEM>
) : PagingSource<Int, ITEM>() {

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
