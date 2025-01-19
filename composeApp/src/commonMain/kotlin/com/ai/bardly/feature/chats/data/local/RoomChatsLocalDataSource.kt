package com.ai.bardly.feature.chats.data.local

import com.ai.bardly.database.DaoExecutor
import com.ai.bardly.domain.games.model.local.toLocalModel
import com.ai.bardly.feature.chats.data.local.model.toDomainModel
import com.ai.bardly.feature.chats.domain.model.MessageDomainModel

class RoomChatsLocalDataSource(
    private val dao: MessagesDao,
    private val daoExecutor: DaoExecutor,
) : ChatsLocalDataSource {

    override suspend fun getMessages(gameId: Int): Result<List<MessageDomainModel>> {
        return daoExecutor.executeDaoOperation {
            dao.getMessage(gameId).map { it.toDomainModel() }
        }
    }

    override suspend fun saveMessage(message: MessageDomainModel): Result<Unit> {
        return daoExecutor.executeDaoOperation {
            dao.insert(message.toLocalModel())
        }
    }
}
