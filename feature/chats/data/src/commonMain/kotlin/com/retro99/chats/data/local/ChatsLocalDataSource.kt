package com.retro99.chats.data.local

import com.retro99.chats.data.local.model.MessageEntity

interface ChatsLocalDataSource {
    suspend fun getMessages(gameId: Int): Result<List<MessageEntity>>
    suspend fun saveMessage(message: MessageEntity): Result<Unit>
    suspend fun getLatestMessagesPerGame(): Result<List<MessageEntity>>
}