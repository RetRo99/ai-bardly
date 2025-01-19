package com.ai.bardly.database

import androidx.room.Room
import kotlinx.cinterop.ExperimentalForeignApi
import org.koin.dsl.module
import platform.Foundation.NSDocumentDirectory
import platform.Foundation.NSFileManager
import platform.Foundation.NSUserDomainMask

actual fun getDatabaseModule() = module {
    single<AppDatabase> {
        val dbFilePath = documentDirectory() + "/bardly.db"
        Room.databaseBuilder<AppDatabase>(
            name = dbFilePath,
        ).fallbackToDestructiveMigration(true)
            .build()
    }
}

@OptIn(ExperimentalForeignApi::class)
private fun documentDirectory(): String {
    val documentDirectory = NSFileManager.defaultManager.URLForDirectory(
        directory = NSDocumentDirectory,
        inDomain = NSUserDomainMask,
        appropriateForURL = null,
        create = false,
        error = null,
    )
    return requireNotNull(documentDirectory?.path)
}
