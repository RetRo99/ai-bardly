package com.ai.bardly.feature.main.chats.data.remote

import com.ai.bardly.feature.main.chats.data.remote.model.MessageDto
import com.ai.bardly.feature.main.chats.data.remote.model.PromptResponseDto
import com.ai.bardly.feature.main.chats.data.remote.model.toRequest
import com.ai.bardly.feature.main.chats.domain.model.MessageType
import com.ai.bardly.networking.NetworkClient
import com.ai.bardly.util.now
import me.tatarka.inject.annotations.Inject
import software.amazon.lastmile.kotlin.inject.anvil.AppScope
import software.amazon.lastmile.kotlin.inject.anvil.ContributesBinding
import software.amazon.lastmile.kotlin.inject.anvil.SingleIn

@Inject
@SingleIn(AppScope::class)
@ContributesBinding(AppScope::class)
class NetworkChatsRemoteDataSource(
    private val networkClient: NetworkClient,
) : ChatsRemoteDataSource {

    override suspend fun getAnswer(message: MessageDto): Result<MessageDto> {
        return networkClient.post<PromptResponseDto>(
            path = "prompt",
            body = message.toRequest()
        ).map {
            MessageDto(
                text = it.data.result.text,
                type = MessageType.Bardly,
                gameId = message.gameId,
                timestamp = now(),
                gameTitle = message.gameTitle
            )
        }
    }
}
