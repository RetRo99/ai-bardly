package com.ai.bardly.feature.chats.data.remote

import com.ai.bardly.feature.chats.domain.model.MessageDomainModel

interface ChatsRemoteDataSource {
    suspend fun getAnswer(message: MessageDomainModel): Result<MessageDomainModel>
}