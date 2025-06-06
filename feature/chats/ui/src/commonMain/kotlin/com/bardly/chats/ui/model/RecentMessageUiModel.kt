package com.bardly.chats.ui.model

import com.retro99.chats.domain.model.RecentMessageDomainModel
import kotlinx.datetime.LocalDateTime

data class RecentMessageUiModel(
    val gameId: String,
    val gameTitle: String,
    val timestamp: LocalDateTime,
    val thumbnail: String,
)

fun List<RecentMessageDomainModel>.toUiModel() = map(RecentMessageDomainModel::toUiModel)

fun RecentMessageDomainModel.toUiModel() = RecentMessageUiModel(
    gameId = gameId,
    gameTitle = gameTitle,
    timestamp = timestamp,
    thumbnail = thumbnail,
)
