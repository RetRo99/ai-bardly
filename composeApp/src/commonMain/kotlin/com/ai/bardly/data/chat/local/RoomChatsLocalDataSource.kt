package com.ai.bardly.data.chat.local

import com.ai.bardly.domain.chats.local.MessagesDao
import com.ai.bardly.domain.chats.local.toDomainModel
import com.ai.bardly.domain.chats.model.MessageDomainModel
import com.ai.bardly.domain.games.model.local.toLocalModel

class RoomChatsLocalDataSource(
    private val dao: MessagesDao,
) : ChatsLocalDataSource {

    override suspend fun getMessages(gameId: String): Result<List<MessageDomainModel>> {
        return try {
            val messages = dao.getMessage(gameId)
            Result.success(messages.map { it.toDomainModel() })
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun saveMessage(message: MessageDomainModel): Result<Unit> {
        return try {
            Result.success(dao.insert(message.toLocalModel()))
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
