package com.retro99.chats.domain

import com.retro99.chats.domain.model.RecentMessageDomainModel
import com.retro99.games.implementation.GamesRepository
import me.tatarka.inject.annotations.Inject
import software.amazon.lastmile.kotlin.inject.anvil.AppScope
import software.amazon.lastmile.kotlin.inject.anvil.SingleIn

@Inject
@SingleIn(AppScope::class)
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