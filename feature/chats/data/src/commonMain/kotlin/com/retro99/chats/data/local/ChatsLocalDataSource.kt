package com.retro99.chats.data.local

import com.retro99.base.result.AppResult
import com.retro99.database.api.message.MessageEntity

interface ChatsLocalDataSource {
    suspend fun getMessages(gameId: Int, limit: Int? = null): AppResult<List<MessageEntity>>
    suspend fun saveMessage(message: MessageEntity): AppResult<Unit>
    suspend fun getLatestMessagesPerGame(): AppResult<List<MessageEntity>>
}