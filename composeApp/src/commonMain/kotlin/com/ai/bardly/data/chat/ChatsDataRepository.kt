package com.ai.bardly.data.chat

import com.ai.bardly.data.chat.local.ChatsLocalDataSource
import com.ai.bardly.data.chat.remote.ChatsRemoteDataSource
import com.ai.bardly.domain.chats.ChatsRepository
import com.ai.bardly.domain.chats.model.MessageDomainModel

class ChatsDataRepository(
    private val remoteChatsDataSource: ChatsRemoteDataSource,
    private val localChatsDataSource: ChatsLocalDataSource,
) : ChatsRepository {
    override suspend fun getAnswerFor(request: MessageDomainModel): Result<MessageDomainModel> {
        localChatsDataSource.saveMessage(request)
        val answerMessage = remoteChatsDataSource.getAnswer(request)
        localChatsDataSource.saveMessage(answerMessage.getOrThrow())
        return answerMessage
    }

    override suspend fun getMessages(gameId: String): Result<List<MessageDomainModel>> {
        return localChatsDataSource.getMessages(gameId)
    }
}
