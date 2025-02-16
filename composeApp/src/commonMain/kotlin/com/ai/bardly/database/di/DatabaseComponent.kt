package com.ai.bardly.database.di

import com.ai.bardly.database.AppDatabase
import com.ai.bardly.database.PlatformDataBaseHelper
import me.tatarka.inject.annotations.Provides
import software.amazon.lastmile.kotlin.inject.anvil.AppScope
import software.amazon.lastmile.kotlin.inject.anvil.ContributesTo
import software.amazon.lastmile.kotlin.inject.anvil.SingleIn

@ContributesTo(AppScope::class)
interface DatabaseComponent {

    @Provides
    @SingleIn(AppScope::class)
    fun provideAppDatabase(dataBaseHelper: PlatformDataBaseHelper): AppDatabase =
        dataBaseHelper.getDatabaseBuilder().build()

    @Provides
    @SingleIn(AppScope::class)
    fun provideGamesDao(appDatabase: AppDatabase) = appDatabase.getGamesDao()

    @Provides
    @SingleIn(AppScope::class)
    fun provideMessagesDao(appDatabase: AppDatabase) = appDatabase.getMessagesDao()
}
