package com.retro99.chats.data

import com.retro99.chats.data.local.ChatsLocalDataSource
import com.retro99.chats.data.local.model.toDomainModel
import com.retro99.chats.data.local.model.toLocalModel
import com.retro99.chats.data.remote.ChatsRemoteDataSource
import com.retro99.chats.data.remote.model.toDomainModel
import com.retro99.chats.data.remote.model.toDto
import com.retro99.chats.domain.ChatsRepository
import com.retro99.chats.domain.model.MessageDomainModel
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
        val saveRequestDeferred = async { localChatsDataSource.saveMessage(request.toLocalModel()) }
        val answerMessage = remoteChatsDataSource
            .getAnswer(request.toDto())
            .map { it.toDomainModel() }
            .onSuccess { answer ->
                localChatsDataSource.saveMessage(
                    answer.toLocalModel()
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
