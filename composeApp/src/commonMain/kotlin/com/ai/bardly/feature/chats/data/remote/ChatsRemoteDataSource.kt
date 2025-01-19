package com.ai.bardly.feature.chats.data.remote

import com.ai.bardly.feature.chats.data.remote.model.MessageDto

interface ChatsRemoteDataSource {
    suspend fun getAnswer(message: MessageDto): Result<MessageDto>
}