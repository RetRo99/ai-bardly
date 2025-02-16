package com.ai.bardly.buildconfig.di

import com.ai.bardly.annotations.Named
import com.ai.bardly.buildconfig.BuildConfig
import com.ai.bardly.buildconfig.getBuildConfig
import me.tatarka.inject.annotations.Provides
import software.amazon.lastmile.kotlin.inject.anvil.AppScope
import software.amazon.lastmile.kotlin.inject.anvil.ContributesTo
import software.amazon.lastmile.kotlin.inject.anvil.SingleIn

@ContributesTo(AppScope::class)
interface BuildConfigComponent {

    @Provides
    @SingleIn(AppScope::class)
    fun provideBuildConfig(): BuildConfig = getBuildConfig()

    @Provides
    @SingleIn(AppScope::class)
    @Named("isDebug")
    fun provideIsDebug(buildConfig: BuildConfig): Boolean = buildConfig.isDebug
}
