package com.ai.bardly.di

import android.app.Application
import android.content.Context
import me.tatarka.inject.annotations.Provides
import software.amazon.lastmile.kotlin.inject.anvil.AppScope
import software.amazon.lastmile.kotlin.inject.anvil.MergeComponent
import software.amazon.lastmile.kotlin.inject.anvil.SingleIn

@MergeComponent(AppScope::class)
@SingleIn(AppScope::class)
abstract class AppComponent(
    @get:Provides val application: Application,
) : ActivityDiComponent.Factory {
    abstract val activityDiComponentFactory: ActivityDiComponent.Factory

    @Provides
    @SingleIn(AppScope::class)
    fun provideContext(application: Application): Context =
        application

    //endregion
    companion object
}
