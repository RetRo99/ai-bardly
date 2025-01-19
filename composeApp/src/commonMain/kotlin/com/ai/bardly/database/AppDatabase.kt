package com.ai.bardly.database

import androidx.room.ConstructedBy
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.RoomDatabaseConstructor
import androidx.room.TypeConverters
import com.ai.bardly.feature.chats.data.local.MessagesDao
import com.ai.bardly.feature.chats.data.local.model.MessageEntity
import com.ai.bardly.feature.games.data.local.GamesDao
import com.ai.bardly.feature.games.data.local.model.GameEntity
import org.koin.core.module.Module

@Database(
    entities = [
        MessageEntity::class,
        GameEntity::class,
    ],
    version = 1
)
@ConstructedBy(AppDatabaseConstructor::class)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun getMessagesDao(): MessagesDao
    abstract fun getGamesDao(): GamesDao
}

@Suppress("NO_ACTUAL_FOR_EXPECT")
expect object AppDatabaseConstructor : RoomDatabaseConstructor<AppDatabase> {
    override fun initialize(): AppDatabase
}

expect fun getDatabaseModule(): Module