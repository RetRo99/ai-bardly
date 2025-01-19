package com.ai.bardly.feature.games.data.local

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.ai.bardly.feature.games.data.local.model.GameEntity

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
}