package com.ai.bardly.database

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import me.tatarka.inject.annotations.Inject
import software.amazon.lastmile.kotlin.inject.anvil.AppScope
import software.amazon.lastmile.kotlin.inject.anvil.SingleIn

@Inject
@SingleIn(AppScope::class)
actual class PlatformDataBaseHelper(
    private val context: Context,
) {
    actual fun getDatabaseBuilder(): RoomDatabase.Builder<AppDatabase> {
        val dbFile = context.getDatabasePath("bardly.db")
        return Room.databaseBuilder<AppDatabase>(
            context = context,
            name = dbFile.absolutePath
        ).fallbackToDestructiveMigration(true)
    }
}