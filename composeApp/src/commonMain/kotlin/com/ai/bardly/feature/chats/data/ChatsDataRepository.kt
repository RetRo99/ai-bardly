package com.ai.bardly.feature.chats.data

import com.ai.bardly.feature.chats.data.local.ChatsLocalDataSource
import com.ai.bardly.feature.chats.data.local.model.toDomainModel
import com.ai.bardly.feature.chats.data.local.model.toEntity
import com.ai.bardly.feature.chats.data.remote.ChatsRemoteDataSource
import com.ai.bardly.feature.chats.data.remote.model.toDomainModel
import com.ai.bardly.feature.chats.data.remote.model.toDto
import com.ai.bardly.feature.chats.domain.ChatsRepository
import com.ai.bardly.feature.chats.domain.model.MessageDomainModel
import com.ai.bardly.feature.chats.domain.model.RecentMessageDomainModel
import com.ai.bardly.feature.games.data.local.GamesLocalDataSource
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope

class ChatsDataRepository(
    private val remoteChatsDataSource: ChatsRemoteDataSource,
    private val localChatsDataSource: ChatsLocalDataSource,
    private val localGamesDataSource: GamesLocalDataSource,
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

    override suspend fun getRecentChats(): Result<List<RecentMessageDomainModel>> {
        return runCatching {
            val recentMessages = localChatsDataSource.getRecentChats().getOrThrow()
            val gameIds = recentMessages.map { it.gameId }.distinct()
            val games = localGamesDataSource.getGamesById(gameIds).getOrThrow()
                .associateBy { it.id }

            recentMessages.map { message ->
                val game = games[message.gameId]
                    ?: throw IllegalStateException("Game not found for id: ${message.gameId}")
                RecentMessageDomainModel(
                    gameId = game.id,
                    gameTitle = game.title,
                    timestamp = message.timestamp,
                    thumbnail = game.thumbnail,
                )
            }
        }
    }
}
