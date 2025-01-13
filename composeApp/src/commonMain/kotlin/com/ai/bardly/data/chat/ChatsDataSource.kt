package com.ai.bardly.data.chat

import com.ai.bardly.domain.chats.model.MessageDomainModel

interface ChatsDataSource {
    suspend fun getAnswer(message: MessageDomainModel): Result<MessageDomainModel>
    suspend fun getMessages(gameId: String): Result<List<MessageDomainModel>>
    suspend fun saveMessage(message: MessageDomainModel): Result<Unit>
}