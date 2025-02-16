package com.ai.bardly.feature.main.chats.domain

import com.ai.bardly.feature.main.chats.domain.model.MessageDomainModel

interface ChatsRepository {
    suspend fun getAnswerFor(request: MessageDomainModel): Result<MessageDomainModel>
    suspend fun getMessages(gameId: Int): Result<List<MessageDomainModel>>
    suspend fun getLatestMessagesPerGame(): Result<List<MessageDomainModel>>
}