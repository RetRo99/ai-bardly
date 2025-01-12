package com.ai.bardly

sealed class MessageUiModel(
    val text: String
) {
    data class UserMessage(
        val message: String
    ) : MessageUiModel(message)

    data class BardlyMessage(
        val message: String
    ) : MessageUiModel(message)
}