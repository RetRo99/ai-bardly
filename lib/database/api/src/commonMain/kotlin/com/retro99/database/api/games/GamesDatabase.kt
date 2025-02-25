package com.retro99.database.api.games

import androidx.paging.PagingSource
import kotlinx.datetime.LocalDateTime

interface GamesDatabase {

    suspend fun insert(item: GameEntity): Result<Unit>

    suspend fun insert(items: List<GameEntity>): Result<Unit>

    suspend fun getGame(id: Int): Result<GameEntity>

    suspend fun getGamesById(ids: List<Int>): Result<List<GameEntity>>

    suspend fun getRecentlyOpenGames(amount: Int): Result<List<GameEntity>>

    suspend fun updateGameOpenTime(id: Int, openedDateTime: LocalDateTime?): Result<Unit>

    suspend fun clearAll(): Result<Unit>

    fun getGames(query: String?): PagingSource<Int, GameEntity>

    suspend fun markAsFavourite(gameId: Int): Result<Unit>

    suspend fun isMarkedAsFavorite(gameId: Int): Result<Boolean>
}