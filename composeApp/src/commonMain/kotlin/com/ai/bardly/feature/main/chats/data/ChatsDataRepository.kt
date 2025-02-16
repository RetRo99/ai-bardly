package com.ai.bardly.feature.main.chats.data

import com.ai.bardly.feature.main.chats.data.local.ChatsLocalDataSource
import com.ai.bardly.feature.main.chats.data.local.model.toDomainModel
import com.ai.bardly.feature.main.chats.data.local.model.toEntity
import com.ai.bardly.feature.main.chats.data.remote.ChatsRemoteDataSource
import com.ai.bardly.feature.main.chats.data.remote.model.toDomainModel
import com.ai.bardly.feature.main.chats.data.remote.model.toDto
import com.ai.bardly.feature.main.chats.domain.ChatsRepository
import com.ai.bardly.feature.main.chats.domain.model.MessageDomainModel
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import me.tatarka.inject.annotations.Inject
import software.amazon.lastmile.kotlin.inject.anvil.AppScope
import software.amazon.lastmile.kotlin.inject.anvil.ContributesBinding
import software.amazon.lastmile.kotlin.inject.anvil.SingleIn

@Inject
@SingleIn(AppScope::class)
@ContributesBinding(AppScope::class)
class ChatsDataRepository(
    private val remoteChatsDataSource: ChatsRemoteDataSource,
    private val localChatsDataSource: ChatsLocalDataSource,
) : ChatsRepository {

    override suspend fun getAnswerFor(
        request: MessageDomainModel
    ): Result<MessageDomainModel> = coroutineScope {
        val saveRequestDeferred = async { localChatsDataSource.saveMessage(request.toEntity()) }
        val answerMessage = remoteChatsDataSource
            .getAnswer(request.toDto())
            .map { it.toDomainModel() }
            .onSuccess { answer ->
                localChatsDataSource.saveMessage(
                    answer.toEntity()
                )
            }

        saveRequestDeferred.await()
        answerMessage
    }

    override suspend fun getMessages(gameId: Int): Result<List<MessageDomainModel>> {
        return localChatsDataSource.getMessages(gameId).map { it.toDomainModel() }
    }

    override suspend fun getLatestMessagesPerGame(): Result<List<MessageDomainModel>> {
        return localChatsDataSource.getLatestMessagesPerGame().map { it.toDomainModel() }
    }
}
