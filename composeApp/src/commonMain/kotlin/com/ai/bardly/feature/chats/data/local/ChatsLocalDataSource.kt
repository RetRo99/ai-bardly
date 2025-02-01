package com.ai.bardly.feature.chats.data.local

import com.ai.bardly.feature.chats.data.local.model.MessageEntity

interface ChatsLocalDataSource {
    suspend fun getMessages(gameId: Int): Result<List<MessageEntity>>
    suspend fun saveMessage(message: MessageEntity): Result<Unit>
    suspend fun getRecentChats(): Result<List<MessageEntity>>
}