package com.ai.bardly.feature.main.chats.ui.model

import com.ai.bardly.feature.main.chats.domain.model.RecentMessageDomainModel
import kotlinx.datetime.LocalDateTime

data class RecentMessageUiModel(
    val gameId: Int,
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