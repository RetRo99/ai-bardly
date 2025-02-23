package com.retro99.database.implementation

import androidx.room.ConstructedBy
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.RoomDatabaseConstructor
import androidx.room.TypeConverters
import com.retro99.database.implementation.dao.games.GamesDao
import com.retro99.database.implementation.dao.games.RoomGameEntity
import com.retro99.database.implementation.dao.games.RoomGameMetadataEntity
import com.retro99.database.implementation.dao.messages.MessageRoomEntity
import com.retro99.database.implementation.dao.messages.MessagesDao

@Database(
    entities = [
        MessageRoomEntity::class,
        RoomGameEntity::class,
        RoomGameMetadataEntity::class,
    ],
    version = 2,
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