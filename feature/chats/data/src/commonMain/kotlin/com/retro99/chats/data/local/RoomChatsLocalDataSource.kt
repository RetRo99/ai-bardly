package com.retro99.chats.data.local

import com.retro99.base.result.AppResult
import com.retro99.database.api.message.MessageEntity
import com.retro99.database.api.message.MessagesDatabase
import me.tatarka.inject.annotations.Inject
import software.amazon.lastmile.kotlin.inject.anvil.AppScope
import software.amazon.lastmile.kotlin.inject.anvil.ContributesBinding
import software.amazon.lastmile.kotlin.inject.anvil.SingleIn

@Inject
@SingleIn(AppScope::class)
@ContributesBinding(AppScope::class)
class RoomChatsLocalDataSource(
    private val database: MessagesDatabase,
) : ChatsLocalDataSource {

    override suspend fun getMessages(gameId: Int, limit: Int?): AppResult<List<MessageEntity>> {
        return database.getMessages(gameId, limit)
    }

    override suspend fun saveMessage(message: MessageEntity): AppResult<Unit> {
        return database.insert(message)
    }

    override suspend fun getLatestMessagesPerGame(): AppResult<List<MessageEntity>> {
        return database.getLatestMessagesPerGame()

    }
}
