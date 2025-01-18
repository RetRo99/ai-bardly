package com.ai.bardly.data.chat.remote

import com.ai.bardly.domain.chats.model.MessageDomainModel

interface ChatsRemoteDataSource {
    suspend fun getAnswer(message: MessageDomainModel): Result<MessageDomainModel>
}