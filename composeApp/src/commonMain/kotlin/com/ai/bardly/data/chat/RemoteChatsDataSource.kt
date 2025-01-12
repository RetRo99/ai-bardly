package com.ai.bardly.data.chat

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
}
