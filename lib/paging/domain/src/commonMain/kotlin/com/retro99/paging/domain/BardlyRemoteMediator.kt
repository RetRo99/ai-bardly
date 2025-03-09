package com.retro99.paging.domain

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
                    try {
                        if (loadType == LoadType.REFRESH) {
                            clearLocal()
                        }
                        saveToLocal(remoteToLocal(remoteResult.data))
                        MediatorResult.Success(
                            endOfPaginationReached = remoteResult.data.isEmpty()
                        )
                    } catch (e: IllegalStateException) {
                        // Specifically catch Room database schema mismatch errors
                        if (e.message?.contains("Room cannot verify the data integrity") == true) {
                            // Since we already have remote data, we can return success with that data
                            // but we couldn't save it locally due to schema mismatch
                            MediatorResult.Success(
                                endOfPaginationReached = remoteResult.data.isEmpty()
                            )
                        } else {
                            // Rethrow if it's a different IllegalStateException
                            throw e
                        }
                    } catch (e: Exception) {
                        // Catch any other exceptions during local operations
                        MediatorResult.Error(e)
                    }
                }
                is PagingSource.LoadResult.Error -> {
                    try {
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
                    } catch (e: IllegalStateException) {
                        // Specifically catch Room database schema mismatch errors
                        if (e.message?.contains("Room cannot verify the data integrity") == true) {
                            // You could handle this specially, e.g., return empty result
                            MediatorResult.Error(e)
                        } else {
                            // Rethrow if it's a different IllegalStateException
                            throw e
                        }
                    } catch (e: Exception) {
                        // Catch any other exceptions that might occur
                        MediatorResult.Error(e)
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