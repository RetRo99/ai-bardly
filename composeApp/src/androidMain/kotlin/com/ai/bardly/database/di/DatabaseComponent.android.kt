package com.ai.bardly.database.di

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import com.ai.bardly.database.AppDatabase
import me.tatarka.inject.annotations.Provides
import software.amazon.lastmile.kotlin.inject.anvil.AppScope
import software.amazon.lastmile.kotlin.inject.anvil.ContributesTo
import software.amazon.lastmile.kotlin.inject.anvil.SingleIn

@ContributesTo(AppScope::class)
@SingleIn(AppScope::class)
interface AndroidDatabaseComponent {

    @Provides
    @SingleIn(AppScope::class)
    fun provideAppDatabase(context: Context): RoomDatabase.Builder<AppDatabase> {
        val dbFile = context.getDatabasePath("bardly.db")
        return Room.databaseBuilder<AppDatabase>(
            context = context,
            name = dbFile.absolutePath
        ).fallbackToDestructiveMigration(true)
    }
}