package com.retro99.database.implementation.di

import androidx.room.RoomDatabase
import com.retro99.database.implementation.AppDatabase
import me.tatarka.inject.annotations.Component
import me.tatarka.inject.annotations.Provides
import software.amazon.lastmile.kotlin.inject.anvil.AppScope
import software.amazon.lastmile.kotlin.inject.anvil.ContributesTo
import software.amazon.lastmile.kotlin.inject.anvil.SingleIn

@ContributesTo(AppScope::class)
@SingleIn(AppScope::class)
@Component
interface DatabaseComponent {

    @Provides
    @SingleIn(AppScope::class)
    fun provideAppDatabase(builder: RoomDatabase.Builder<AppDatabase>): AppDatabase {
        return builder.build()
    }
}
