package com.retro99.chats.data.remote.model

import com.retro99.chats.domain.model.PromptRequestDomainModel
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PromptRequestDto(
    val question: String,
    @SerialName("game")
    val gameTitle: String,
    val history: List<MessageDto>?,
)

fun PromptRequestDomainModel.toDto(): PromptRequestDto {
    return PromptRequestDto(
        question = question,
        gameTitle = gameTitle,
        history = history.map { it.toDto() }.takeUnless { it.isEmpty() }
    )
}