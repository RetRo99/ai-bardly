package com.ai.bardly.paging

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import app.cash.paging.PagingSource
import app.cash.paging.PagingState
import kotlinx.coroutines.flow.Flow

class CustomPager<ITEM : PagingItem>(
    config: PagingConfig,
    initialKey: Int,
    getItems: suspend (Int, Int) -> PagingResult<ITEM>
) {
    val pagingData: Flow<PagingData<ITEM>> = Pager(
        config = config,
        pagingSourceFactory = {
            CustomPagingSource(
                initialKey,
                getItems
            )
        }
    ).flow

    class CustomPagingSource<ITEM : PagingItem>(
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
            return state.anchorPosition
        }
    }
}