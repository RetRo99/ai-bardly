package com.ai.bardly.database

import androidx.room.ConstructedBy
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.RoomDatabaseConstructor
import androidx.room.TypeConverters
import com.ai.bardly.feature.main.chats.data.local.MessagesDao
import com.ai.bardly.feature.main.chats.data.local.model.MessageEntity
import com.ai.bardly.feature.main.games.data.local.GamesDao
import com.ai.bardly.feature.main.games.data.local.model.GameEntity
import com.ai.bardly.feature.main.games.data.local.model.GameMetadataEntity

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

expect class PlatformDataBaseHelper {
    fun getDatabaseBuilder(): RoomDatabase.Builder<AppDatabase>
}