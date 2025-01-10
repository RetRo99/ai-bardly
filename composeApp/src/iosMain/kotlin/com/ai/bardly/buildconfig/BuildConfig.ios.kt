package com.ai.bardly.buildconfig

import kotlin.experimental.ExperimentalNativeApi

class BuildConfigIos : BuildConfig {
    @OptIn(ExperimentalNativeApi::class)
    override val isDebug: Boolean
        get() = Platform.isDebugBinary
}

actual fun getBuildConfig(): BuildConfig = BuildConfigIos()