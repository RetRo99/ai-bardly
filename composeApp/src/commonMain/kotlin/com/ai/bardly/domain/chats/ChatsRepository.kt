package com.ai.bardly.domain.chats

import com.ai.bardly.domain.chats.model.MessageDomainModel

interface ChatsRepository {
    suspend fun getAnswerFor(request: MessageDomainModel): Result<MessageDomainModel>

    suspend fun getMessages(id: String): Result<List<MessageDomainModel>>
}