package com.retro99.database.implementation.di

import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import com.retro99.database.implementation.AppDatabase
import com.retro99.database.implementation.toremove.Named
import kotlinx.cinterop.ExperimentalForeignApi
import me.tatarka.inject.annotations.Provides
import platform.Foundation.NSDocumentDirectory
import platform.Foundation.NSFileManager
import platform.Foundation.NSUserDomainMask
import software.amazon.lastmile.kotlin.inject.anvil.AppScope
import software.amazon.lastmile.kotlin.inject.anvil.ContributesTo
import software.amazon.lastmile.kotlin.inject.anvil.SingleIn

@ContributesTo(AppScope::class)
@SingleIn(AppScope::class)
interface IosDatabaseComponent {

    @OptIn(ExperimentalForeignApi::class)
    @Provides
    @Named("documentDirectory")
    fun provideDocumentDirectory(): String = requireNotNull(
        NSFileManager.defaultManager.URLForDirectory(
            directory = NSDocumentDirectory,
            inDomain = NSUserDomainMask,
            appropriateForURL = null,
            create = false,
            error = null,
        )?.path
    )

    @Provides
    @SingleIn(AppScope::class)
    fun provideAppDatabase(@Named("documentDirectory") documentDirectory: String): RoomDatabase.Builder<AppDatabase> {
        val dbFilePath = "$documentDirectory/bardly.db"
        return Room.databaseBuilder<AppDatabase>(
            name = dbFilePath,
        ).fallbackToDestructiveMigration(true)
            .setDriver(BundledSQLiteDriver())
    }
}
