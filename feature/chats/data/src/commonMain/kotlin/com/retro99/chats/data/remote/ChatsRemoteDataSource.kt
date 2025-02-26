package com.retro99.chats.data.remote

import com.retro99.base.result.AppResult
import com.retro99.chats.data.remote.model.MessageDto

interface ChatsRemoteDataSource {
    suspend fun getAnswer(message: MessageDto): AppResult<MessageDto>
}