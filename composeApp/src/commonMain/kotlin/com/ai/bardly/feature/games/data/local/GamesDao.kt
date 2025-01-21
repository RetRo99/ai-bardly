package com.ai.bardly.feature.games.data.local

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.ai.bardly.feature.games.data.local.model.GameEntity
import kotlinx.datetime.LocalDateTime

@Dao
interface GamesDao {
    @Insert
    suspend fun insert(item: GameEntity)

    @Insert
    suspend fun insert(items: List<GameEntity>)

    @Query("SELECT * FROM GameEntity WHERE id = :id")
    suspend fun getGame(id: Int): GameEntity

    @Query("SELECT * FROM GameEntity")
    fun getGames(): PagingSource<Int, GameEntity>

    @Query("DELETE FROM GameEntity")
    suspend fun clearAll()

    @Query("SELECT * FROM GameEntity WHERE lastOpenTime IS NOT NULL ORDER BY lastOpenTime DESC LIMIT :amount")
    suspend fun getRecentlyOpenGames(amount: Int): List<GameEntity>

    @Query("UPDATE GameEntity SET lastOpenTime = :openedDateTime WHERE id = :id")
    suspend fun updateGameOpenTime(id: Int, openedDateTime: LocalDateTime)
}