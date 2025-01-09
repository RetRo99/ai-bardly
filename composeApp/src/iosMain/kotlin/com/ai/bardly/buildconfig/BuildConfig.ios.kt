package com.ai.bardly.buildconfig

class BuildConfigIos : BuildConfig {
    override val isDebug: Boolean
        get() = NSProcessInfo.processInfo.environment["IS_DEBUG"] == "true"
}

actual fun getBuildConfig(): BuildConfig = BuildConfigIos()