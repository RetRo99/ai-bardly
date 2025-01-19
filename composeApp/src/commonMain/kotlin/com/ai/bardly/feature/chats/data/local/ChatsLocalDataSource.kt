package com.ai.bardly.feature.chats.data.local

import com.ai.bardly.feature.chats.domain.model.MessageDomainModel

interface ChatsLocalDataSource {
    suspend fun getMessages(gameId: Int): Result<List<MessageDomainModel>>
    suspend fun saveMessage(message: MessageDomainModel): Result<Unit>
}