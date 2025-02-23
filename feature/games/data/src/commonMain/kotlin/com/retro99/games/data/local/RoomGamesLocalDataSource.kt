package com.retro99.games.data.local

import androidx.paging.PagingSource
import com.retro99.database.api.games.GameEntity
import com.retro99.database.api.games.GamesDatabase
import com.retro99.games.data.local.model.GameMetadataLocalModel
import kotlinx.datetime.LocalDateTime
import me.tatarka.inject.annotations.Inject
import software.amazon.lastmile.kotlin.inject.anvil.AppScope
import software.amazon.lastmile.kotlin.inject.anvil.ContributesBinding
import software.amazon.lastmile.kotlin.inject.anvil.SingleIn

@Inject
@SingleIn(AppScope::class)
@ContributesBinding(AppScope::class)
class RoomGamesLocalDataSource(
    private val gamesDatabase: GamesDatabase,
) : GamesLocalDataSource {

    override fun getGames(query: String?): PagingSource<Int, GameEntity> {
        return gamesDatabase.getGames(query)
    }

    override suspend fun getGamesById(ids: List<Int>): Result<List<GameEntity>> {
        return gamesDatabase.getGamesById(ids)
    }

    override suspend fun getGame(id: Int): Result<GameEntity> {
        return gamesDatabase.getGame(id)
    }

    override suspend fun saveGames(games: List<GameEntity>): Result<Unit> {
        return gamesDatabase.insert(games)
    }

    override suspend fun getRecentlyOpenGames(amount: Int): Result<List<GameEntity>> {
        return gamesDatabase.getRecentlyOpenGames(amount)
    }

    override suspend fun clearAll() {
        gamesDatabase.clearAll()
    }

    override suspend fun updateGameOpenTime(id: Int, openedDateTime: LocalDateTime): Result<Unit> {
        return gamesDatabase.updateGameOpenTime(GameMetadataLocalModel(id, openedDateTime))
    }
}