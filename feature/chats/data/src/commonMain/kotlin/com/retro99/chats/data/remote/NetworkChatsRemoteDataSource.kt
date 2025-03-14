package com.retro99.chats.data.remote

import com.github.michaelbull.result.map
import com.retro99.base.result.AppResult
import com.retro99.chats.data.remote.model.MessageDto
import com.retro99.chats.data.remote.model.PromptResponseDto
import com.retro99.chats.data.remote.model.toRequest
import me.tatarka.inject.annotations.Inject
import retro99.games.api.NetworkClient
import retro99.games.api.post
import software.amazon.lastmile.kotlin.inject.anvil.AppScope
import software.amazon.lastmile.kotlin.inject.anvil.ContributesBinding
import software.amazon.lastmile.kotlin.inject.anvil.SingleIn

@Inject
@SingleIn(AppScope::class)
@ContributesBinding(AppScope::class)
class NetworkChatsRemoteDataSource(
    private val networkClient: NetworkClient,
) : ChatsRemoteDataSource {

    override suspend fun getAnswer(message: MessageDto): AppResult<MessageDto> {
        return networkClient.post<PromptResponseDto>(
            path = "prompt",
            body = message.toRequest()
        ).map {
            message.copy(
                answer = it.data.result.text,
            )
        }
    }
}
