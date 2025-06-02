package com.retro99.database.api.games

import androidx.paging.PagingSource
import com.retro99.base.result.AppResult
import kotlinx.datetime.LocalDateTime

interface GamesDatabase {

    suspend fun insert(item: GameEntity): AppResult<Unit>

    suspend fun insert(items: List<GameEntity>): AppResult<Unit>

    suspend fun getGame(id: String): AppResult<GameEntity>

    suspend fun getGamesById(ids: List<String>): AppResult<List<GameEntity>>

    suspend fun getRecentlyOpenGames(amount: Int): AppResult<List<GameEntity>>

    suspend fun updateGameOpenTime(id: String, openedDateTime: LocalDateTime?): AppResult<Unit>

    suspend fun clearAll(): AppResult<Unit>

    fun getGames(query: String?): PagingSource<Int, GameEntity>

    suspend fun addToFavourites(gameId: String): AppResult<Unit>

    suspend fun removeFromFavourites(gameId: String): AppResult<Unit>

    suspend fun isMarkedAsFavorite(gameId: String): AppResult<Boolean>
}
