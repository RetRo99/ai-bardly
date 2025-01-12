package com.ai.bardly.data.chat.remote

import com.ai.bardly.data.chat.ChatsDataSource
import com.ai.bardly.data.chat.model.PromptResponseApiModel
import com.ai.bardly.data.chat.model.toRequest
import com.ai.bardly.domain.chats.model.MessageDomainModel
import com.ai.bardly.domain.chats.model.MessageType
import com.ai.bardly.networking.NetworkClient
import com.ai.bardly.util.now

class RemoteChatsDataSource(
    private val networkClient: NetworkClient,
) : ChatsDataSource {

    override suspend fun getAnswer(message: MessageDomainModel): Result<MessageDomainModel> {
        return networkClient.post<PromptResponseApiModel>(
            path = "prompt",
            body = message.toRequest()
        ).map {
            MessageDomainModel(
                text = it.data.result.text,
                type = MessageType.Bardly,
                id = message.id,
                timestamp = now()
            )
        }
    }

    override suspend fun getMessages(id: String): Result<List<MessageDomainModel>> {
        throw NotImplementedError("RemoteChatsDataSource is not for getting messages")
    }

    override suspend fun saveMessage(message: MessageDomainModel): Result<Unit> {
        throw NotImplementedError("RemoteChatsDataSource is not for saving messages")
    }
}
