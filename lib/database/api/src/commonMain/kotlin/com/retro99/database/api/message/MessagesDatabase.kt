package com.retro99.database.api.message

import com.retro99.base.result.AppResult
import com.retro99.base.result.CompletableResult

interface MessagesDatabase {

    suspend fun insert(item: MessageEntity): CompletableResult

    suspend fun insert(items: List<MessageEntity>): CompletableResult

    suspend fun getMessages(gameId: Int, limit: Int?): AppResult<List<MessageEntity>>

    suspend fun getLatestMessagesPerGame(): AppResult<List<MessageEntity>>
}