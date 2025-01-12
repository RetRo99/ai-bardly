package com.ai.bardly.data.chat

import com.ai.bardly.data.chat.model.QuestionRequestApiModel

class ChatsRepository(
    private val gamesApi: ChatsDataSource,
) {
    suspend fun getAnswer(request: QuestionRequestApiModel): Result<String> =
        gamesApi.getAnswer(request)
}
