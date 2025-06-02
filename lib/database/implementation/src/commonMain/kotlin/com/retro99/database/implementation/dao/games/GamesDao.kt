package com.retro99.database.implementation.dao.games

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import kotlinx.datetime.LocalDateTime

@Dao
interface GamesDao {

    @Insert(onConflict = OnConflictStrategy.Companion.REPLACE)
    suspend fun insert(item: RoomGameEntity)

    @Insert(onConflict = OnConflictStrategy.Companion.REPLACE)
    suspend fun insert(items: List<RoomGameEntity>)

    @Query("SELECT * FROM RoomGameEntity WHERE id = :id")
    suspend fun getGame(id: String): RoomGameEntity

    @Query("SELECT * FROM RoomGameEntity WHERE id IN (:ids)")
    suspend fun getGamesById(ids: List<String>): List<RoomGameEntity>

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
    suspend fun updateGameMetaData(game: RoomGameMetadataEntity)

    @Transaction
    suspend fun updateIsFavorite(gameId: String, isFavorite: Boolean) {
        val existing = getMetadataByGameId(gameId)
        val metadata =
            existing?.copy(isFavourite = isFavorite) ?: RoomGameMetadataEntity(
                gameId,
                null,
                isFavorite
            )
        updateGameMetaData(metadata)
    }

    @Transaction
    suspend fun updateGameOpenTime(gameId: String, dateTime: LocalDateTime?) {
        val existing = getMetadataByGameId(gameId)
        val metadata = existing?.copy(lastOpenTime = dateTime) ?: RoomGameMetadataEntity(
            gameId,
            dateTime,
            false
        )
        updateGameMetaData(metadata)
    }

    @Query("SELECT * FROM RoomGameMetadataEntity WHERE gameId = :gameId")
    suspend fun getMetadataByGameId(gameId: String): RoomGameMetadataEntity?

    @Query(
        """
    SELECT isFavourite FROM RoomGameMetadataEntity
    WHERE gameId = :gameId
    """
    )
    suspend fun isMarkedAsFavorite(gameId: String): Boolean?
}
