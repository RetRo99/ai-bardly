package com.ai.bardly.feature.main.chats.data.remote

import com.ai.bardly.feature.main.chats.data.remote.model.MessageDto

interface ChatsRemoteDataSource {
    suspend fun getAnswer(message: MessageDto): Result<MessageDto>
}