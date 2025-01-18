package com.ai.bardly.data.chat.remote

import com.ai.bardly.data.chat.model.PromptResponseApiModel
import com.ai.bardly.data.chat.model.toRequest
import com.ai.bardly.domain.chats.model.MessageDomainModel
import com.ai.bardly.domain.chats.model.MessageType
import com.ai.bardly.networking.NetworkClient
import com.ai.bardly.util.now

class NetworkChatsRemoteDataSource(
    private val networkClient: NetworkClient,
) : ChatsRemoteDataSource {

    override suspend fun getAnswer(message: MessageDomainModel): Result<MessageDomainModel> {
        return networkClient.post<PromptResponseApiModel>(
            path = "prompt",
            body = message.toRequest()
        ).map {
            MessageDomainModel(
                text = it.data.result.text,
                type = MessageType.Bardly,
                gameId = message.gameId,
                timestamp = now(),
                gameTitle = message.gameTitle
            )
        }
    }
}
