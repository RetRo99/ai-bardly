package com.retro99.database.implementation

import me.tatarka.inject.annotations.Provides
import software.amazon.lastmile.kotlin.inject.anvil.AppScope
import software.amazon.lastmile.kotlin.inject.anvil.ContributesTo
import software.amazon.lastmile.kotlin.inject.anvil.SingleIn

@ContributesTo(AppScope::class)
@SingleIn(AppScope::class)
interface DaoComponent {

    @Provides
    @SingleIn(AppScope::class)
    fun provideGamesDao(appDatabase: AppDatabase) = appDatabase.getGamesDao()

    @Provides
    @SingleIn(AppScope::class)
    fun provideMessagesDao(appDatabase: AppDatabase) = appDatabase.getMessagesDao()

    @Provides
    @SingleIn(AppScope::class)
    fun provideShelfsDao(appDatabase: AppDatabase) = appDatabase.getShelfsDao()
}
