package com.ai.bardly.feature.chats.domain

import com.ai.bardly.feature.chats.domain.model.RecentMessageDomainModel
import com.ai.bardly.feature.games.domain.GamesRepository
import org.koin.core.annotation.Single

@Single
class GetRecentChatsUseCase(
    private val chatsRepository: ChatsRepository,
    private val gamesRepository: GamesRepository
) {

    suspend operator fun invoke(): Result<List<RecentMessageDomainModel>> {
        return runCatching {
            val recentMessages = chatsRepository.getLatestMessagesPerGame().getOrThrow()
            val gameIds = recentMessages.map { it.gameId }.distinct()
            val games = gamesRepository.getGamesById(gameIds).getOrThrow()
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