package com.ai.bardly.feature.chats.data.local

import com.ai.bardly.database.DaoExecutor
import com.ai.bardly.feature.chats.data.local.model.MessageEntity
import org.koin.core.annotation.Single

@Single(binds = [ChatsLocalDataSource::class])
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
