package com.retro99.chats.domain.model

data class PromptRequestDomainModel(
    val question: String,
    val gameTitle: String,
    val history: List<MessageDomainModel>,
)