package com.ai.bardly.di

import com.ai.bardly.RootViewController
import com.ai.bardly.annotations.ActivityScope
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.essenty.lifecycle.coroutines.coroutineScope
import kotlinx.coroutines.CoroutineScope
import me.tatarka.inject.annotations.Provides
import software.amazon.lastmile.kotlin.inject.anvil.AppScope
import software.amazon.lastmile.kotlin.inject.anvil.ContributesSubcomponent
import software.amazon.lastmile.kotlin.inject.anvil.SingleIn

@ContributesSubcomponent(ActivityScope::class)
@SingleIn(ActivityScope::class)
interface IosViewPresenterComponent {
    val rootViewController: RootViewController

    @Provides
    @SingleIn(ActivityScope::class)
    fun provideAppCoroutineScope(
        componentContext: ComponentContext
    ): CoroutineScope = componentContext.coroutineScope()

    @ContributesSubcomponent.Factory(AppScope::class)
    interface Factory {
        fun createComponent(
            componentContext: ComponentContext,
        ): IosViewPresenterComponent
    }
}
