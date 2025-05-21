package com.retro99.shelfs.data

import com.github.michaelbull.result.Err
import com.github.michaelbull.result.Ok
import com.github.michaelbull.result.andThen
import com.github.michaelbull.result.coroutines.coroutineBinding
import com.github.michaelbull.result.onFailure
import com.github.michaelbull.result.onSuccess
import com.retro99.base.result.AppResult
import com.retro99.base.result.CompletableResult
import com.retro99.database.api.shelfs.ShelfEntity
import com.retro99.games.data.local.GamesLocalDataSource
import com.retro99.games.data.local.model.toDomainModel
import com.retro99.games.data.local.model.toLocalModel
import com.retro99.games.data.remote.model.toDomainModel
import com.retro99.shelfs.data.local.ShelfsLocalDataSource
import com.retro99.shelfs.data.local.model.toLocalModel
import com.retro99.shelfs.data.remote.ShelfsRemoteDataSource
import com.retro99.shelfs.data.remote.model.toDomainModel
import com.retro99.shelfs.domain.ShelfsRepository
import com.retro99.shelfs.domain.model.ShelfDomainModel
import kotlinx.coroutines.flow.Flow
import me.tatarka.inject.annotations.Inject
import software.amazon.lastmile.kotlin.inject.anvil.AppScope
import software.amazon.lastmile.kotlin.inject.anvil.ContributesBinding
import software.amazon.lastmile.kotlin.inject.anvil.SingleIn

@Inject
@SingleIn(AppScope::class)
@ContributesBinding(AppScope::class)
class ShelfsDataRepository(
    private val remoteSource: ShelfsRemoteDataSource,
    private val localSource: ShelfsLocalDataSource,
    private val gamesLocalSource: GamesLocalDataSource,
) : ShelfsRepository {

    override suspend fun getShelf(id: String): Flow<AppResult<ShelfDomainModel>> {
        return fetchWithCacheFirst(
            getCached = { getCachedShelf(id) },
            fetchRemote = { hasCachedData, emitter ->
                fetchShelfFromRemoteAndUpdateCache(id, hasCachedData, emitter)
            }
        )
    }

    override suspend fun getShelfs(): Flow<AppResult<List<ShelfDomainModel>>> {
        return fetchWithCacheFirst(
            getCached = { getCachedShelfs() },
            fetchRemote = { hasCachedData, emitter ->
                fetchRemoteShelfsAndUpdateCache(hasCachedData, emitter)
            })
    }

    override suspend fun addGameToShelf(shelfId: String, gameId: String): CompletableResult {
        return remoteSource.addGameToShelf(shelfId, gameId)
    }

    private suspend fun getCachedShelf(id: String): AppResult<ShelfDomainModel>? {
        return localSource.getShelf(id)
            .andThen { cachedShelf ->
                resolveShelfGames(cachedShelf)
            }
    }

    private suspend fun resolveShelfGames(cachedShelf: ShelfEntity): AppResult<ShelfDomainModel> {
        return coroutineBinding {
            // Get games for this shelf
            val gameIds = cachedShelf.games
            val resolvedGames = gamesLocalSource.getGamesById(gameIds).bind()
            val gameMap = resolvedGames.associate { game -> game.id to game.toDomainModel() }

            // Create the domain model with resolved games
            ShelfDomainModel(
                id = cachedShelf.id,
                name = cachedShelf.name,
                games = cachedShelf.games.mapNotNull { gameId -> gameMap[gameId] })
        }
    }

    private suspend fun fetchShelfFromRemoteAndUpdateCache(
        id: String,
        hasCachedData: Boolean,
        emit: suspend (AppResult<ShelfDomainModel>) -> Unit
    ) {
        remoteSource.getShelf(id).onSuccess { shelfDto ->
            // Save the shelf to local cache
            localSource.save(shelfDto.toLocalModel())

            // Save the games to local cache
            val games = shelfDto.games
            gamesLocalSource.saveGames(
                games.toDomainModel().toLocalModel()
            )

            // Emit the updated data
            emit(Ok(shelfDto.toDomainModel()))
        }.onFailure { error ->
            // Only emit error if we don't have cached data
            if (!hasCachedData) {
                emit(Err(error))
            }
        }
    }

    private suspend fun getCachedShelfs(): AppResult<List<ShelfDomainModel>> {
        return coroutineBinding {
            val cachedShelfs = localSource.getShelfs().bind()

            // Extract needed game IDs
            val neededGamesId = cachedShelfs.flatMap { it.games }.toMutableSet().toList()

            // Get games and create a map of game IDs to domain models
            val resolvedGames = gamesLocalSource.getGamesById(neededGamesId).bind()
            val gameMap = resolvedGames.associate { game -> game.id to game.toDomainModel() }

            // Map shelf entities to domain models
            cachedShelfs.map { cachedShelf ->
                ShelfDomainModel(
                    id = cachedShelf.id,
                    name = cachedShelf.name,
                    games = cachedShelf.games.mapNotNull { gameId -> gameMap[gameId] })
            }
        }
    }

    private suspend fun fetchRemoteShelfsAndUpdateCache(
        hasCachedData: Boolean,
        emit: suspend (AppResult<List<ShelfDomainModel>>) -> Unit
    ) {
        remoteSource
            .getShelfs()
            .onSuccess { remoteShelfs ->
                // Save remote data to local cache
                localSource.save(remoteShelfs.toLocalModel())
                val allGames = remoteShelfs.flatMap { it.games }
                gamesLocalSource.saveGames(
                    allGames.toDomainModel().toLocalModel()
                )

                // Emit the updated data
                emit(Ok(remoteShelfs.toDomainModel()))
            }.onFailure { error ->
                // Only emit error if we don't have cached data
                if (!hasCachedData) {
                    emit(Err(error))
                }
            }
    }

}
