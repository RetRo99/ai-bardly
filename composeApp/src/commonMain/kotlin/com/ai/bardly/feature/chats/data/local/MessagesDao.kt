package com.ai.bardly.feature.chats.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.ai.bardly.feature.chats.data.local.model.MessageEntity

@Dao
interface MessagesDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(item: MessageEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(items: List<MessageEntity>)

    @Query("SELECT * FROM MessageEntity WHERE gameId = :gameId ORDER BY timestamp DESC")
    suspend fun getMessage(gameId: Int): List<MessageEntity>
}