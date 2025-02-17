package com.ai.bardly.di

import com.arkivanov.essenty.backhandler.BackDispatcher
import me.tatarka.inject.annotations.Provides
import software.amazon.lastmile.kotlin.inject.anvil.AppScope
import software.amazon.lastmile.kotlin.inject.anvil.MergeComponent
import software.amazon.lastmile.kotlin.inject.anvil.MergeComponent.CreateComponent
import software.amazon.lastmile.kotlin.inject.anvil.SingleIn
import kotlin.reflect.KClass

@MergeComponent(AppScope::class)
@SingleIn(AppScope::class)
abstract class IosApplicationComponent : IosViewPresenterComponent.Factory {
    abstract val componentFactory: IosViewPresenterComponent.Factory
    abstract val backDispatcher: BackDispatcher

    @Provides
    @SingleIn(AppScope::class)
    fun provideBackDispatcher(): BackDispatcher {
        return BackDispatcher()
    }

    companion object {
        fun create() = IosApplicationComponent::class.createComponent()
    }
}

/**
 * The `actual fun` will be generated for each iOS specific target. See [MergeComponent] for more
 * details.
 */
@CreateComponent
expect fun KClass<IosApplicationComponent>.createComponent(): IosApplicationComponent
