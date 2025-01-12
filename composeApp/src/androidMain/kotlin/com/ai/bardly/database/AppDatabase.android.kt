package com.ai.bardly.database

import androidx.room.Room
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

actual fun getDatabaseModule() = module {
    single<AppDatabase> {
        val dbFile = androidContext().getDatabasePath("bardly.db")
        Room.databaseBuilder<AppDatabase>(
            context = androidContext(),
            name = dbFile.absolutePath
        ).build()
    }
}
