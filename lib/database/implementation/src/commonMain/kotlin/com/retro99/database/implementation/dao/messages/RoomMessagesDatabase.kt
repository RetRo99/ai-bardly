package com.retro99.database.implementation.dao.messages

import com.retro99.base.result.AppResult
import com.retro99.database.api.message.MessageEntity
import com.retro99.database.api.message.MessagesDatabase
import com.retro99.database.implementation.DatabaseExecutor
import me.tatarka.inject.annotations.Inject
import software.amazon.lastmile.kotlin.inject.anvil.AppScope
import software.amazon.lastmile.kotlin.inject.anvil.ContributesBinding
import software.amazon.lastmile.kotlin.inject.anvil.SingleIn

@Inject
@SingleIn(AppScope::class)
@ContributesBinding(AppScope::class)
class RoomMessagesDatabase(
    private val dao: MessagesDao,
    private val daoExecutor: DatabaseExecutor,
) : MessagesDatabase {
    override suspend fun insert(item: MessageEntity): AppResult<Unit> {
        return daoExecutor.executeDatabaseOperation {
            dao.insert(item.toRoomEntity())
        }
    }

    override suspend fun insert(items: List<MessageEntity>): AppResult<Unit> {
        return daoExecutor.executeDatabaseOperation {
            dao.insert(items.toRoomEntity())
        }
    }

    override suspend fun getMessage(gameId: Int): AppResult<List<MessageEntity>> {
        return daoExecutor.executeDatabaseOperation {
            dao.getMessage(gameId)
        }
    }

    override suspend fun getLatestMessagesPerGame(): AppResult<List<MessageEntity>> {
        return daoExecutor.executeDatabaseOperation {
            dao.getLatestMessagesPerGame()
        }
    }
}