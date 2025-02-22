package com.retro99.database.implementation

import software.amazon.lastmile.kotlin.inject.anvil.AppScope
import software.amazon.lastmile.kotlin.inject.anvil.ContributesTo
import software.amazon.lastmile.kotlin.inject.anvil.SingleIn

@ContributesTo(AppScope::class)
@SingleIn(AppScope::class)
interface DaoComponent {

    // TODO: Implement your DAOs here
//    @Provides
//    @SingleIn(AppScope::class)
//    fun provideGamesDao(appDatabase: AppDatabase) = appDatabase.getGamesDao()
//
//    @Provides
//    @SingleIn(AppScope::class)
//    fun provideMessagesDao(appDatabase: AppDatabase) = appDatabase.getMessagesDao()
}
