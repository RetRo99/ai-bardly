package com.ai.bardly.data.chat

import com.ai.bardly.domain.chats.local.MessagesDao
import com.ai.bardly.domain.chats.local.toDomainModel
import com.ai.bardly.domain.chats.local.toLocalModel
import com.ai.bardly.domain.chats.model.MessageDomainModel

class LocalChatsDataSource(
    private val dao: MessagesDao,
) : ChatsDataSource {

    override suspend fun getAnswer(message: MessageDomainModel): Result<MessageDomainModel> {
        throw NotImplementedError("LocalChatsDataSource is not for getting answers")
    }

    override suspend fun getMessages(id: String): Result<List<MessageDomainModel>> {
        return try {
            val messages = dao.getMessages(id)
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
