package com.retro99.database.implementation.dao.messages

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface MessagesDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(item: MessageRoomEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(items: List<MessageRoomEntity>)

    @Query("SELECT * FROM MessageRoomEntity WHERE gameId = :gameId ORDER BY timestamp DESC")
    suspend fun getAllMessages(gameId: Int): List<MessageRoomEntity>

    @Query("SELECT * FROM MessageRoomEntity WHERE gameId = :gameId ORDER BY timestamp DESC LIMIT :limit")
    suspend fun getMessagesWithLimit(gameId: Int, limit: Int): List<MessageRoomEntity>

    @Query(
        """
            SELECT * FROM MessageRoomEntity 
            WHERE timestamp IN (
                SELECT MAX(timestamp) FROM MessageRoomEntity 
                GROUP BY gameId
            ) 
            ORDER BY timestamp DESC
         """
    )
    suspend fun getLatestMessagesPerGame(): List<MessageRoomEntity>
}