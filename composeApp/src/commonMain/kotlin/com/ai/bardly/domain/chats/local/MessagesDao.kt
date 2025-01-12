package com.ai.bardly.domain.chats.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface MessagesDao {
    @Insert
    suspend fun insert(item: MessageEntity)

    @Query("SELECT * FROM MessageEntity WHERE id = :id ORDER BY timestamp DESC")
    suspend fun getMessages(id: String): List<MessageEntity>
}