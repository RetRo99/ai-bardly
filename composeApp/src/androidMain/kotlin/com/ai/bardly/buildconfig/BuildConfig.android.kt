package com.ai.bardly.buildconfig


class BuildConfigAndroid: BuildConfig {
    override val isDebug: Boolean
        get() = com.ai.bardly.BuildConfig.DEBUG
}
actual fun getBuildConfig(): BuildConfig = BuildConfigAndroid()