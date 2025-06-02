package com.retro99.games.data

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.github.michaelbull.result.map
import com.github.michaelbull.result.onFailure
import com.github.michaelbull.result.onSuccess
import com.retro99.base.now
import com.retro99.base.result.AppResult
import com.retro99.base.result.CompletableResult
import com.retro99.base.result.andThenAlways
import com.retro99.games.data.local.GamesLocalDataSource
import com.retro99.games.data.local.model.toDomainModel
import com.retro99.games.data.local.model.toLocalModel
import com.retro99.games.data.remote.GamesRemoteDataSource
import com.retro99.games.data.remote.model.toDomainModel
import com.retro99.games.domain.GamesRepository
import com.retro99.games.domain.model.GameDomainModel
import com.retro99.paging.domain.BardlyRemoteMediator
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import me.tatarka.inject.annotations.Inject
import software.amazon.lastmile.kotlin.inject.anvil.AppScope
import software.amazon.lastmile.kotlin.inject.anvil.ContributesBinding
import software.amazon.lastmile.kotlin.inject.anvil.SingleIn

@Inject
@SingleIn(AppScope::class)
@ContributesBinding(AppScope::class)
class GamesDataRepository(
    private val remoteSource: GamesRemoteDataSource,
    private val localSource: GamesLocalDataSource,
) : GamesRepository {

    @OptIn(ExperimentalPagingApi::class)
    override suspend fun getGames(query: String?): Flow<PagingData<GameDomainModel>> {
        val remotePagingSource = remoteSource.getGames(query)
        return Pager(
            config = PagingConfig(
                pageSize = 20,
                enablePlaceholders = false,
                initialLoadSize = 20
            ),
            remoteMediator = BardlyRemoteMediator(
                remoteSource = remotePagingSource,
                localSource = localSource.getGames(query),
                saveToLocal = { localSource.saveGames(it) },
                remoteToLocal = { it.toDomainModel().toLocalModel() }
            ),
            pagingSourceFactory = { localSource.getGames(query) }
        ).flow.toDomainModel()
    }

    override suspend fun getRecentlyOpenGames(amount: Int): AppResult<List<GameDomainModel>> {
        return localSource.getRecentlyOpenGames(amount).map { it.toDomainModel() }
    }

    override suspend fun getGamesById(ids: List<String>): AppResult<List<GameDomainModel>> {
        return localSource.getGamesById(ids).map { it.toDomainModel() }
    }

    override suspend fun addToFavourites(gameId: String): CompletableResult {
        return remoteSource.addToFavourites(gameId)
            .onSuccess {
                localSource.addToFavourites(gameId)
            }
    }

    override suspend fun removeFromFavourites(gameId: String): CompletableResult {
        return remoteSource.removeFromFavourites(gameId)
            .onSuccess {
                localSource.removeFromFavourites(gameId)
            }
    }

    override suspend fun updateGameOpenDate(gameId: String): CompletableResult {
        return localSource.updateGameOpenTime(gameId, now())
    }

    override fun isMarkedAsFavorite(gameId: String): Flow<Boolean> {
        return flow {
            localSource.isMarkedAsFavorite(gameId)
                .onSuccess { localFavoriteStatus ->
                    emit(localFavoriteStatus)
                }
                .andThenAlways { localResult ->
                    remoteSource.isMarkedAsFavorite(gameId)
                        .onSuccess { remoteFavoriteStatus ->
                            if (localResult.isErr || localResult.value != remoteFavoriteStatus) {
                                emit(remoteFavoriteStatus)
                            }
                            if (remoteFavoriteStatus) {
                                localSource.addToFavourites(gameId)
                            } else {
                                localSource.removeFromFavourites(gameId)
                            }
                        }
                        .onFailure {
                            if (localResult.isErr) {
                                emit(false)
                            }
                        }
                }
        }
    }
}
