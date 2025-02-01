package com.ai.bardly.feature.chats.domain

import com.ai.bardly.feature.chats.domain.model.MessageDomainModel
import com.ai.bardly.feature.chats.domain.model.RecentMessageDomainModel

interface ChatsRepository {
    suspend fun getAnswerFor(request: MessageDomainModel): Result<MessageDomainModel>

    suspend fun getMessages(gameId: Int): Result<List<MessageDomainModel>>
    suspend fun getRecentChats(): Result<List<RecentMessageDomainModel>>
}