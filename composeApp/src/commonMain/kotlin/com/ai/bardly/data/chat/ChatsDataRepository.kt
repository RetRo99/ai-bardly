package com.ai.bardly.data.chat

import com.ai.bardly.domain.chats.ChatsRepository
import com.ai.bardly.domain.chats.model.MessageDomainModel

class ChatsDataRepository(
    private val remoteChatsDataSource: ChatsDataSource,
    private val localChatsDataSource: ChatsDataSource,
) : ChatsRepository {
    override suspend fun getAnswerFor(request: MessageDomainModel): Result<MessageDomainModel> {
        localChatsDataSource.saveMessage(request)
        val answerMessage = remoteChatsDataSource.getAnswer(request)
        localChatsDataSource.saveMessage(answerMessage.getOrThrow())
        return answerMessage
    }

    override suspend fun getMessages(id: String): Result<List<MessageDomainModel>> {
        return localChatsDataSource.getMessages(id)
    }
}
