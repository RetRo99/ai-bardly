package com.ai.bardly.paging

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingSource
import androidx.paging.PagingState
import androidx.paging.RemoteMediator

@OptIn(ExperimentalPagingApi::class)
class BardlyRemoteMediator<ITEM : PagingItem>(
    private val remoteSource: PagingSource<Int, ITEM>,
    private val localSource: PagingSource<Int, ITEM>,
    private val saveToLocal: suspend (List<ITEM>) -> Unit,
    private val clearLocal: suspend () -> Unit,
    private val shouldRefresh: () -> Boolean = { true }
) : RemoteMediator<Int, ITEM>() {

    private var currentPage = 1

    override suspend fun initialize(): InitializeAction {
        return if (shouldRefresh()) {
            InitializeAction.LAUNCH_INITIAL_REFRESH
        } else {
            InitializeAction.SKIP_INITIAL_REFRESH
        }
    }

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, ITEM>
    ): MediatorResult {
        return try {
            // Create type-safe load params
            val loadParams = when (loadType) {
                LoadType.REFRESH -> {
                    currentPage = 1
                    PagingSource.LoadParams.Refresh<Int>(
                        key = currentPage,
                        loadSize = state.config.initialLoadSize,
                        placeholdersEnabled = false
                    )
                }

                LoadType.APPEND -> {
                    currentPage++
                    PagingSource.LoadParams.Append(
                        key = currentPage,
                        loadSize = state.config.pageSize,
                        placeholdersEnabled = false
                    )
                }

                LoadType.PREPEND -> {
                    return MediatorResult.Success(endOfPaginationReached = true)
                }
            }

            val remoteResult = remoteSource.load(loadParams)

            when (remoteResult) {
                is PagingSource.LoadResult.Page -> {
                    if (loadType == LoadType.REFRESH) {
                        clearLocal()
                    }
                    saveToLocal(remoteResult.data)

                    MediatorResult.Success(
                        endOfPaginationReached = remoteResult.data.isEmpty()
                    )
                }

                is PagingSource.LoadResult.Error -> {
                    val localResult = localSource.load(loadParams)

                    when (localResult) {
                        is PagingSource.LoadResult.Page -> {
                            MediatorResult.Success(
                                endOfPaginationReached = localResult.data.isEmpty()
                            )
                        }

                        is PagingSource.LoadResult.Error -> {
                            MediatorResult.Error(localResult.throwable)
                        }

                        is PagingSource.LoadResult.Invalid -> {
                            MediatorResult.Error(IllegalStateException("Invalid local source"))
                        }
                    }
                }

                is PagingSource.LoadResult.Invalid -> {
                    MediatorResult.Error(IllegalStateException("Invalid remote source"))
                }
            }
        } catch (e: Exception) {
            MediatorResult.Error(e)
        }
    }
}