package com.retro99.games.data.local

import androidx.paging.PagingSource
import com.retro99.base.result.ThrowableResult
import com.retro99.database.api.games.GameEntity
import kotlinx.datetime.LocalDateTime

interface GamesLocalDataSource {
    fun getGames(query: String?): PagingSource<Int, GameEntity>

    suspend fun getGamesById(ids: List<Int>): ThrowableResult<List<GameEntity>>

    suspend fun getGame(id: Int): ThrowableResult<GameEntity>

    suspend fun saveGames(games: List<GameEntity>): ThrowableResult<Unit>

    suspend fun getRecentlyOpenGames(amount: Int): ThrowableResult<List<GameEntity>>

    suspend fun clearAll()

    suspend fun updateGameOpenTime(id: Int, openedDateTime: LocalDateTime?): ThrowableResult<Unit>

    suspend fun addToFavourites(gameId: Int): ThrowableResult<Unit>
    suspend fun removeFromFavourites(gameId: Int): ThrowableResult<Unit>

    suspend fun isMarkedAsFavorite(gameId: Int): ThrowableResult<Boolean>
}