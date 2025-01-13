package com.ai.bardly.domain.chats.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface MessagesDao {
    @Insert
    suspend fun insert(item: MessageLocalModel)

    @Insert
    suspend fun insert(items: List<MessageLocalModel>)

    @Query("SELECT * FROM MessageLocalModel WHERE gameId = :gameId ORDER BY timestamp DESC")
    suspend fun getMessage(gameId: String): List<MessageLocalModel>
}