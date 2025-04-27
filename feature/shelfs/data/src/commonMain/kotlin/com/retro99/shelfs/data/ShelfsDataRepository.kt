package com.retro99.shelfs.data

import com.github.michaelbull.result.Err
import com.github.michaelbull.result.Ok
import com.github.michaelbull.result.coroutines.coroutineBinding
import com.github.michaelbull.result.fold
import com.github.michaelbull.result.get
import com.github.michaelbull.result.map
import com.github.michaelbull.result.onFailure
import com.github.michaelbull.result.onSuccess
import com.github.michaelbull.result.toResultOr
import com.retro99.base.result.AppError
import com.retro99.base.result.AppResult
import com.retro99.database.api.games.GameEntity
import com.retro99.database.api.shelfs.ShelfEntity
import com.retro99.games.data.local.GamesLocalDataSource
import com.retro99.games.data.local.model.toDomainModel
import com.retro99.games.data.local.model.toLocalModel
import com.retro99.games.data.remote.model.GameDto
import com.retro99.games.data.remote.model.toDomainModel
import com.retro99.games.domain.model.GameDomainModel
import com.retro99.shelfs.data.local.ShelfsLocalDataSource
import com.retro99.shelfs.data.local.model.toLocalModel
import com.retro99.shelfs.data.remote.ShelfsRemoteDataSource
import com.retro99.shelfs.data.remote.model.ShelfDto
import com.retro99.shelfs.data.remote.model.toDomainModel
import com.retro99.shelfs.domain.ShelfsRepository
import com.retro99.shelfs.domain.model.ShelfDomainModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.flow.flow
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

    override suspend fun getShelf(id: Int): AppResult<ShelfDomainModel> {
        TODO("Not yet implemented")
    }

    override suspend fun getShelfs(): Flow<AppResult<List<ShelfDomainModel>>> {
        return flow {
            val cachedShelf = getCachedShelfs()
            emit(cachedShelf)

            // Check if we have non-empty cached data using fold
            val hasCachedData = cachedShelf.fold(
                success = { data -> data.isNotEmpty() },
                failure = { false }
            )

            fetchRemoteShelfsAndUpdateCache(hasCachedData) { result -> 
                emit(result) 
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
                    cachedShelf.id,
                    cachedShelf.name,
                    cachedShelf.games.mapNotNull { gameId -> gameMap[gameId] }
                )
            }
        }
    }

    private suspend fun fetchRemoteShelfsAndUpdateCache(
        hasCachedData: Boolean,
        emit: suspend (AppResult<List<ShelfDomainModel>>) -> Unit
    ) {
        remoteSource.getShelfs()
            .onSuccess { remoteShelfs ->
                // Save remote data to local cache
                localSource.save(remoteShelfs.toLocalModel())
                val allGames = remoteShelfs.flatMap { it.games }
                gamesLocalSource.saveGames(
                    allGames.toDomainModel().toLocalModel()
                )

                // Emit the updated data
                emit(Ok(remoteShelfs.toDomainModel()))
            }
            .onFailure { error ->
                // Only emit error if we don't have cached data
                if (!hasCachedData) {
                    emit(Err(error))
                }
            }
    }
}
