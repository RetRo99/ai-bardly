package com.retro99.chats.domain

import com.github.michaelbull.result.coroutines.coroutineBinding
import com.retro99.base.result.AppResult
import com.retro99.chats.domain.model.RecentMessageDomainModel
import com.retro99.games.domain.GamesRepository
import me.tatarka.inject.annotations.Inject
import software.amazon.lastmile.kotlin.inject.anvil.AppScope
import software.amazon.lastmile.kotlin.inject.anvil.SingleIn

@Inject
@SingleIn(AppScope::class)
class GetRecentChatsUseCase(
    private val chatsRepository: ChatsRepository,
    private val gamesRepository: GamesRepository
) {
    suspend operator fun invoke(): AppResult<List<RecentMessageDomainModel>> {
        return coroutineBinding {
            val recentMessages = chatsRepository.getLatestMessagesPerGame().bind()

            if (recentMessages.isEmpty()) {
                return@coroutineBinding emptyList()
            }

            val gameIds = recentMessages.map { it.gameId }.distinct()
            val games = gamesRepository.getGamesById(gameIds).bind()
                .associateBy { it.id }

            recentMessages.mapNotNull { message ->
                val game = games[message.gameId] ?: return@mapNotNull null

                RecentMessageDomainModel(
                    gameId = game.id,
                    gameTitle = game.title,
                    timestamp = message.timestamp,
                    thumbnail = game.thumbnail
                )
            }
        }
    }
}
