package com.ai.bardly.feature.chats.data.remote

import com.ai.bardly.feature.chats.data.remote.model.PromptResponseApiModel
import com.ai.bardly.feature.chats.data.remote.model.toRequest
import com.ai.bardly.feature.chats.domain.model.MessageDomainModel
import com.ai.bardly.feature.chats.domain.model.MessageType
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
