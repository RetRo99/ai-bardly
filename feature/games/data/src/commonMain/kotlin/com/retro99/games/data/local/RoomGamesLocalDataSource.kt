package com.retro99.games.data.local

import androidx.paging.PagingSource
import com.retro99.base.result.AppResult
import com.retro99.database.api.games.GameEntity
import com.retro99.database.api.games.GamesDatabase
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

    override suspend fun getGamesById(ids: List<String>): AppResult<List<GameEntity>> {
        return gamesDatabase.getGamesById(ids)
    }

    override suspend fun getGame(id: String): AppResult<GameEntity> {
        return gamesDatabase.getGame(id)
    }

    override suspend fun saveGames(games: List<GameEntity>): AppResult<Unit> {
        return gamesDatabase.insert(games)
    }

    override suspend fun getRecentlyOpenGames(amount: Int): AppResult<List<GameEntity>> {
        return gamesDatabase.getRecentlyOpenGames(amount)
    }

    override suspend fun clearAll() {
        gamesDatabase.clearAll()
    }

    override suspend fun updateGameOpenTime(
        id: String,
        openedDateTime: LocalDateTime?
    ): AppResult<Unit> {
        return gamesDatabase.updateGameOpenTime(id, openedDateTime)
    }

    override suspend fun addToFavourites(gameId: String): AppResult<Unit> {
        return gamesDatabase.addToFavourites(gameId)
    }

    override suspend fun removeFromFavourites(gameId: String): AppResult<Unit> {
        return gamesDatabase.removeFromFavourites(gameId)
    }

    override suspend fun isMarkedAsFavorite(gameId: String): AppResult<Boolean> {
        return gamesDatabase.isMarkedAsFavorite(gameId)
    }
}
