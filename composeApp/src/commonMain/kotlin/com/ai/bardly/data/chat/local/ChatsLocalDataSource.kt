package com.ai.bardly.data.chat.local

import com.ai.bardly.domain.chats.model.MessageDomainModel

interface ChatsLocalDataSource {
    suspend fun getMessages(gameId: String): Result<List<MessageDomainModel>>
    suspend fun saveMessage(message: MessageDomainModel): Result<Unit>
}