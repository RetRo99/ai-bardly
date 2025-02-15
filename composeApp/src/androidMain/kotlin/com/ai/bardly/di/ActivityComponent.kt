package com.ai.bardly.di

import androidx.activity.ComponentActivity
import com.ai.bardly.App
import com.ai.bardly.BardlyApplication
import com.ai.bardly.annotations.ActivityScope
import com.ai.bardly.navigation.root.application.DecomposeRoot
import com.ai.bardly.navigation.root.application.DefaultDecomposeRoot
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.defaultComponentContext
import me.tatarka.inject.annotations.Provides
import software.amazon.lastmile.kotlin.inject.anvil.AppScope
import software.amazon.lastmile.kotlin.inject.anvil.ContributesSubcomponent
import software.amazon.lastmile.kotlin.inject.anvil.SingleIn

@ContributesSubcomponent(ActivityScope::class)
@SingleIn(ActivityScope::class)
interface ActivityComponent {
    @Provides
    fun provideComponentContext(
        activity: ComponentActivity
    ): ComponentContext = activity.defaultComponentContext()

    @Provides
    fun provideDecomposeRoot(
        componentContext: ComponentContext
    ): DecomposeRoot = DefaultDecomposeRoot(componentContext)

    @ContributesSubcomponent.Factory(AppScope::class)
    interface Factory {
        fun createComponent(
            activity: ComponentActivity
        ): ActivityComponent
    }

    val app: App

    companion object {
        fun create(activity: ComponentActivity): ActivityComponent =
            (activity.application as BardlyApplication)
                .getApplicationComponent()
                .activityComponentFactory
                .createComponent(activity)
    }
}
