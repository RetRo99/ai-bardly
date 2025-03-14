package com.retro99.chats.data.remote

import com.retro99.base.result.AppResult
import com.retro99.chats.data.remote.model.MessageDto
import com.retro99.chats.domain.model.PromptRequestDomainModel

interface ChatsRemoteDataSource {
    suspend fun getAnswer(request: PromptRequestDomainModel): AppResult<MessageDto>
}