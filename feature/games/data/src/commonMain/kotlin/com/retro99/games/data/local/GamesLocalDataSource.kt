package com.retro99.games.data.local

import androidx.paging.PagingSource
import com.retro99.database.api.games.GameEntity
import kotlinx.datetime.LocalDateTime

interface GamesLocalDataSource {
    fun getGames(query: String?): PagingSource<Int, GameEntity>

    suspend fun getGamesById(ids: List<Int>): Result<List<GameEntity>>

    suspend fun getGame(id: Int): Result<GameEntity>

    suspend fun saveGames(games: List<GameEntity>): Result<Unit>

    suspend fun getRecentlyOpenGames(amount: Int): Result<List<GameEntity>>

    suspend fun clearAll()

    suspend fun updateGameOpenTime(id: Int, openedDateTime: LocalDateTime?): Result<Unit>

    suspend fun markAsFavourite(gameId: Int): Result<Unit>

    suspend fun isMarkedAsFavorite(gameId: Int): Result<Boolean>
}