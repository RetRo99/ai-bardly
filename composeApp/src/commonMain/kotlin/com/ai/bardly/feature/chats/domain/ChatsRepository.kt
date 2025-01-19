package com.ai.bardly.feature.chats.domain

import com.ai.bardly.feature.chats.domain.model.MessageDomainModel

interface ChatsRepository {
    suspend fun getAnswerFor(request: MessageDomainModel): Result<MessageDomainModel>

    suspend fun getMessages(gameId: Int): Result<List<MessageDomainModel>>
}