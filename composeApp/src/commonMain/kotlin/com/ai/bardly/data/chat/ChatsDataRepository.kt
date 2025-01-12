package com.ai.bardly.data.chat

import com.ai.bardly.domain.chats.ChatsRepository
import com.ai.bardly.domain.chats.model.MessageDomainModel

class ChatsDataRepository(
    private val gamesApi: ChatsDataSource,
) : ChatsRepository {
    override suspend fun getAnswerFor(request: MessageDomainModel): Result<MessageDomainModel> =
        gamesApi.getAnswer(request)
}
