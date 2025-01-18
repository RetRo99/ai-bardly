package com.ai.bardly.data.chat

import com.ai.bardly.data.chat.local.ChatsLocalDataSource
import com.ai.bardly.data.chat.remote.ChatsRemoteDataSource
import com.ai.bardly.domain.chats.ChatsRepository
import com.ai.bardly.domain.chats.model.MessageDomainModel
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope

class ChatsDataRepository(
    private val remoteChatsDataSource: ChatsRemoteDataSource,
    private val localChatsDataSource: ChatsLocalDataSource,
) : ChatsRepository {

    override suspend fun getAnswerFor(
        request: MessageDomainModel
    ): Result<MessageDomainModel> = coroutineScope {
        val saveRequestDeferred = async { localChatsDataSource.saveMessage(request) }
        val answerMessage = remoteChatsDataSource.getAnswer(request)
        val saveAnswerDeferred =
            async { localChatsDataSource.saveMessage(answerMessage.getOrThrow()) }
        saveAnswerDeferred.await()
        saveRequestDeferred.await()
        answerMessage
    }

    override suspend fun getMessages(gameId: Int): Result<List<MessageDomainModel>> {
        return localChatsDataSource.getMessages(gameId)
    }
}
