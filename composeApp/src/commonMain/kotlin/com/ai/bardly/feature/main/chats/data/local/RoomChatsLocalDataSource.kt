package com.ai.bardly.feature.main.chats.data.local

import com.ai.bardly.database.DaoExecutor
import com.ai.bardly.feature.main.chats.data.local.model.MessageEntity
import me.tatarka.inject.annotations.Inject
import software.amazon.lastmile.kotlin.inject.anvil.AppScope
import software.amazon.lastmile.kotlin.inject.anvil.ContributesBinding
import software.amazon.lastmile.kotlin.inject.anvil.SingleIn

@Inject
@SingleIn(AppScope::class)
@ContributesBinding(AppScope::class)
class RoomChatsLocalDataSource(
    private val dao: MessagesDao,
    private val daoExecutor: DaoExecutor,
) : ChatsLocalDataSource {

    override suspend fun getMessages(gameId: Int): Result<List<MessageEntity>> {
        return daoExecutor.executeDaoOperation {
            dao.getMessage(gameId)
        }
    }

    override suspend fun saveMessage(message: MessageEntity): Result<Unit> {
        return daoExecutor.executeDaoOperation {
            dao.insert(message)
        }
    }

    override suspend fun getLatestMessagesPerGame(): Result<List<MessageEntity>> {
        return daoExecutor.executeDaoOperation {
            dao.getLatestMessagesPerGame()
        }
    }
}
