package com.ai.bardly.di

import androidx.activity.ComponentActivity
import com.ai.bardly.BardlyApplication
import com.ai.bardly.annotations.ActivityScope
import com.ai.bardly.app.App
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.defaultComponentContext
import me.tatarka.inject.annotations.Provides
import software.amazon.lastmile.kotlin.inject.anvil.AppScope
import software.amazon.lastmile.kotlin.inject.anvil.ContributesSubcomponent
import software.amazon.lastmile.kotlin.inject.anvil.SingleIn

@ContributesSubcomponent(ActivityScope::class)
@SingleIn(ActivityScope::class)
interface ActivityDiComponent {
    @Provides
    fun provideComponentContext(
        activity: ComponentActivity
    ): ComponentContext = activity.defaultComponentContext()

    @ContributesSubcomponent.Factory(AppScope::class)
    interface Factory {
        fun createComponent(
            activity: ComponentActivity
        ): ActivityDiComponent
    }

    val app: App

    companion object {
        fun create(activity: ComponentActivity): ActivityDiComponent =
            (activity.application as BardlyApplication)
                .getApplicationComponent()
                .activityDiComponentFactory
                .createComponent(activity)
    }
}
