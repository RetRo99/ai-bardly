package com.ai.bardly.feature.games.data.local

import androidx.paging.PagingSource
import com.ai.bardly.feature.games.data.local.model.GameEntity
import kotlinx.datetime.LocalDateTime

interface GamesLocalDataSource {
    fun getGames(query: String?): PagingSource<Int, GameEntity>

    suspend fun getGamesById(ids: List<Int>): Result<List<GameEntity>>

    suspend fun getGame(id: Int): Result<GameEntity>

    suspend fun saveGames(games: List<GameEntity>)

    suspend fun getRecentlyOpenGames(amount: Int): Result<List<GameEntity>>

    suspend fun clearAll()

    suspend fun updateGameOpenTime(id: Int, openedDateTime: LocalDateTime): Result<Unit>
}