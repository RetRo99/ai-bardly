package com.retro99.chats.data

import com.github.michaelbull.result.coroutines.coroutineBinding
import com.github.michaelbull.result.map
import com.retro99.base.result.AppResult
import com.retro99.chats.data.local.ChatsLocalDataSource
import com.retro99.chats.data.local.model.toDomainModel
import com.retro99.chats.data.local.model.toLocalModel
import com.retro99.chats.data.remote.ChatsRemoteDataSource
import com.retro99.chats.domain.ChatsRepository
import com.retro99.chats.domain.model.MessageDomainModel
import com.retro99.chats.domain.model.PromptRequestDomainModel
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
    ): AppResult<MessageDomainModel> = coroutineBinding {

        val messagesHistory = localChatsDataSource
            .getMessages(request.gameId, 5)
            .bind()

        val messageWithAnswer = remoteChatsDataSource
            .getAnswer(
                PromptRequestDomainModel(
                    question = request.question,
                    gameTitle = request.gameTitle,
                    history = messagesHistory.toDomainModel()
                )
            )
            .map { test ->
                request.copy(answer = test.answer)
            }
            .bind()

        localChatsDataSource.saveMessage(messageWithAnswer.toLocalModel()).bind()

        messageWithAnswer
    }

    override suspend fun getMessages(gameId: String): AppResult<List<MessageDomainModel>> {
        return localChatsDataSource.getMessages(gameId).map { it.toDomainModel() }
    }

    override suspend fun getLatestMessagesPerGame(): AppResult<List<MessageDomainModel>> {
        return localChatsDataSource.getLatestMessagesPerGame().map { it.toDomainModel() }
    }
}
