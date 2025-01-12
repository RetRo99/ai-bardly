package com.ai.bardly.data.chat

import com.ai.bardly.data.chat.model.QuestionRequestApiModel

interface ChatsDataSource {
    suspend fun getAnswer(request: QuestionRequestApiModel): Result<String>
}