package com.ai.bardly.feature.games.data.local

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.ai.bardly.feature.games.data.local.model.GameEntity
import com.ai.bardly.feature.games.data.local.model.GameMetadataEntity

@Dao
interface GamesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(item: GameEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(items: List<GameEntity>)

    @Query("SELECT * FROM GameEntity WHERE id = :id")
    suspend fun getGame(id: Int): GameEntity

    @Query("SELECT * FROM GameEntity")
    fun getGames(): PagingSource<Int, GameEntity>

    @Query("DELETE FROM GameEntity")
    suspend fun clearAll()

    @Query(
        """
        SELECT * FROM GameEntity
        WHERE id IN (
            SELECT gameId FROM GameMetadataEntity 
            WHERE lastOpenTime IS NOT NULL
            ORDER BY lastOpenTime DESC
            LIMIT :amount
        )
        ORDER BY (
            SELECT lastOpenTime FROM GameMetadataEntity WHERE GameEntity.id = GameMetadataEntity.gameId
        ) DESC
    """
    )
    suspend fun getRecentlyOpenGames(amount: Int): List<GameEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateGameOpenTime(game: GameMetadataEntity)
}