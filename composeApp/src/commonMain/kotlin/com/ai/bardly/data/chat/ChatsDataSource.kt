package com.ai.bardly.data.chat

import com.ai.bardly.domain.chats.model.MessageDomainModel

interface ChatsDataSource {
    suspend fun getAnswer(message: MessageDomainModel): Result<MessageDomainModel>
}