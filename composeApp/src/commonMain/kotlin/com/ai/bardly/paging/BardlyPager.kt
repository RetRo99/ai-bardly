package com.ai.bardly.paging

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow

class BardlyPager<ITEM : PagingItem>(
    config: PagingConfig,
    remoteSource: BardlyPagingSource<ITEM>,
    remoteMediator: BardlyRemoteMediator<ITEM>? = null
) {
    @OptIn(ExperimentalPagingApi::class)
    val pagingData: Flow<PagingData<ITEM>> = Pager(
        config = config,
        remoteMediator = remoteMediator,
        pagingSourceFactory = {
            remoteSource
        }
    ).flow
}