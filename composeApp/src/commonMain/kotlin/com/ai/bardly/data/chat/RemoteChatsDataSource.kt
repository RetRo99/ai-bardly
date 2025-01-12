package com.ai.bardly.data.chat

import com.ai.bardly.data.chat.model.PromptResponseApiModel
import com.ai.bardly.data.chat.model.QuestionRequestApiModel
import com.ai.bardly.networking.NetworkClient

class RemoteChatsDataSource(
    private val networkClient: NetworkClient,
) : ChatsDataSource {

    override suspend fun getAnswer(request: QuestionRequestApiModel): Result<String> {
        return networkClient.post<PromptResponseApiModel>(
            path = "prompt",
            body = request
        ).map { it.data.result.text }
    }

}
