package com.retro99.database.implementation

import androidx.room.ConstructedBy
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.RoomDatabaseConstructor
import androidx.room.TypeConverters
import com.retro99.chats.data.local.MessagesDao
import com.retro99.chats.data.local.model.MessageEntity
import com.retro99.games.data.local.GamesDao
import com.retro99.games.data.local.model.GameEntity
import com.retro99.games.data.local.model.GameMetadataEntity

@Database(
    entities = [
        MessageEntity::class,
        GameEntity::class,
        GameMetadataEntity::class,
    ],
    version = 1,
    exportSchema = true,
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