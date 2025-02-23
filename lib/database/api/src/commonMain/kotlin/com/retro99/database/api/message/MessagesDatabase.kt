package com.retro99.database.api.message

interface MessagesDatabase {

    suspend fun insert(item: MessageEntity): Result<Unit>

    suspend fun insert(items: List<MessageEntity>): Result<Unit>

    suspend fun getMessage(gameId: Int): Result<List<MessageEntity>>

    suspend fun getLatestMessagesPerGame(): Result<List<MessageEntity>>
}