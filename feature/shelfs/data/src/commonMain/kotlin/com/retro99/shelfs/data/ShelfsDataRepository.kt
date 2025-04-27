package com.retro99.shelfs.data

import com.github.michaelbull.result.coroutines.coroutineBinding
import com.github.michaelbull.result.map
import com.github.michaelbull.result.onSuccess
import com.retro99.base.result.AppResult
import com.retro99.games.data.local.GamesLocalDataSource
import com.retro99.games.data.local.model.toDomainModel
import com.retro99.games.data.local.model.toLocalModel
import com.retro99.games.data.remote.model.GameDto
import com.retro99.games.data.remote.model.toDomainModel
import com.retro99.shelfs.data.local.ShelfsLocalDataSource
import com.retro99.shelfs.data.local.model.toLocalModel
import com.retro99.shelfs.data.remote.ShelfsRemoteDataSource
import com.retro99.shelfs.data.remote.model.toDomainModel
import com.retro99.shelfs.domain.ShelfsRepository
import com.retro99.shelfs.domain.model.ShelfDomainModel
import kotlinx.coroutines.flow.Flow
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
            val cachedShelf = coroutineBinding {
                val cachedShelfs = localSource.getShelfs().bind()

                val neededGamesId = mutableSetOf<Int>()
                cachedShelfs.forEach { shelf ->
                    neededGamesId.addAll(shelf.games)
                }

                val resolvedGames = gamesLocalSource.getGamesById(neededGamesId.toList()).bind()

                val gameMap = resolvedGames.associate { game ->
                    game.id to game.toDomainModel()
                }

                cachedShelfs.map { cachedShelf ->
                    ShelfDomainModel(
                        cachedShelf.id,
                        cachedShelf.name,
                        cachedShelf.games.mapNotNull { gameId -> gameMap[gameId] }
                    )
                }
            }
            emit(cachedShelf)

//            emit(
//                remoteSource.getShelfs()
//                    .onSuccess { remoteShelfs ->
//                        localSource.save(remoteShelfs.toLocalModel())
//
//                        val allGames = mutableListOf<GameDto>()
//                        remoteShelfs.forEach { shelf ->
//                            allGames.addAll(shelf.games)
//                        }
//
//                        gamesLocalSource.saveGames(
//                            allGames.toDomainModel().toLocalModel()
//                        )
//                    }.map { it.toDomainModel() }
//            )
        }
    }
}