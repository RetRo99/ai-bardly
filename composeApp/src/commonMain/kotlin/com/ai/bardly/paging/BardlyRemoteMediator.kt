package com.ai.bardly.paging

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingSource
import androidx.paging.PagingState
import androidx.paging.RemoteMediator

@OptIn(ExperimentalPagingApi::class)
class BardlyRemoteMediator<RemoteItem : PagingItem, LocalItem : PagingItem>(
    private val remoteSource: PagingSource<Int, RemoteItem>,
    private val localSource: PagingSource<Int, LocalItem>,
    private val saveToLocal: suspend (List<LocalItem>) -> Unit,
    private val remoteToLocal: (List<RemoteItem>) -> List<LocalItem>,
    private val clearLocal: suspend () -> Unit = { },
    private val shouldRefresh: () -> Boolean = { true }
) : RemoteMediator<Int, LocalItem>() {

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
        state: PagingState<Int, LocalItem>
    ): MediatorResult {
        return try {
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

            when (val remoteResult = remoteSource.load(loadParams)) {
                is PagingSource.LoadResult.Page -> {
                    if (loadType == LoadType.REFRESH) {
                        clearLocal()
                    }
                    saveToLocal(remoteToLocal(remoteResult.data))

                    MediatorResult.Success(
                        endOfPaginationReached = remoteResult.data.isEmpty()
                    )
                }

                is PagingSource.LoadResult.Error -> {
                    when (val localResult = localSource.load(loadParams)) {
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