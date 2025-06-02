package com.retro99.chats.domain

import com.retro99.base.result.AppResult
import com.retro99.chats.domain.model.MessageDomainModel

interface ChatsRepository {
    suspend fun getAnswerFor(request: MessageDomainModel): AppResult<MessageDomainModel>
    suspend fun getMessages(gameId: String): AppResult<List<MessageDomainModel>>
    suspend fun getLatestMessagesPerGame(): AppResult<List<MessageDomainModel>>
}
