package com.ai.bardly.domain.games.model.local

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface GamesDao {
    @Insert
    suspend fun insert(item: GameLocalModel)

    @Insert
    suspend fun insert(items: List<GameLocalModel>)

    @Query("SELECT * FROM GameLocalModel WHERE id = :id")
    suspend fun getGame(id: Int): GameLocalModel

    @Query("SELECT * FROM GameLocalModel")
    fun getGames(): PagingSource<Int, GameLocalModel>

    @Query("DELETE FROM GameLocalModel")
    suspend fun clearAll()
}