package com.retro99.database.implementation.dao.games

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface GamesDao {

    @Insert(onConflict = OnConflictStrategy.Companion.REPLACE)
    suspend fun insert(item: RoomGameEntity)

    @Insert(onConflict = OnConflictStrategy.Companion.REPLACE)
    suspend fun insert(items: List<RoomGameEntity>)

    @Query("SELECT * FROM RoomGameEntity WHERE id = :id")
    suspend fun getGame(id: Int): RoomGameEntity

    @Query("SELECT * FROM RoomGameEntity WHERE id IN (:ids)")
    suspend fun getGamesById(ids: List<Int>): List<RoomGameEntity>

    @Query(
        """
    SELECT * FROM RoomGameEntity 
    WHERE (:query IS NULL OR title LIKE '%' || :query || '%')
"""
    )
    fun getGames(query: String?): PagingSource<Int, RoomGameEntity>

    @Query("DELETE FROM RoomGameEntity")
    suspend fun clearAll()

    @Query(
        """
        SELECT * FROM RoomGameEntity
        WHERE id IN (
            SELECT gameId FROM RoomGameMetadataEntity 
            WHERE lastOpenTime IS NOT NULL
            ORDER BY lastOpenTime DESC
            LIMIT :amount
        )
        ORDER BY (
            SELECT lastOpenTime FROM RoomGameMetadataEntity WHERE RoomGameEntity.id = RoomGameMetadataEntity.gameId
        ) DESC
    """
    )
    suspend fun getRecentlyOpenGames(amount: Int): List<RoomGameEntity>

    @Insert(onConflict = OnConflictStrategy.Companion.REPLACE)
    suspend fun updateGameOpenTime(game: RoomGameMetadataEntity)
}