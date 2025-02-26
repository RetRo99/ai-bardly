package com.retro99.games.data.local

import androidx.paging.PagingSource
import com.retro99.base.result.AppResult
import com.retro99.database.api.games.GameEntity
import kotlinx.datetime.LocalDateTime

interface GamesLocalDataSource {
    fun getGames(query: String?): PagingSource<Int, GameEntity>

    suspend fun getGamesById(ids: List<Int>): AppResult<List<GameEntity>>

    suspend fun getGame(id: Int): AppResult<GameEntity>

    suspend fun saveGames(games: List<GameEntity>): CompletableResult

    suspend fun getRecentlyOpenGames(amount: Int): AppResult<List<GameEntity>>

    suspend fun clearAll()

    suspend fun updateGameOpenTime(id: Int, openedDateTime: LocalDateTime?): CompletableResult

    suspend fun addToFavourites(gameId: Int): CompletableResult
    suspend fun removeFromFavourites(gameId: Int): CompletableResult

    suspend fun isMarkedAsFavorite(gameId: Int): AppResult<Boolean>
}