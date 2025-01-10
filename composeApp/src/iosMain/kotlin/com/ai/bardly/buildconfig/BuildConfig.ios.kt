package com.ai.bardly.buildconfig

import platform.Foundation.NSProcessInfo

class BuildConfigIos : BuildConfig {
    override val isDebug: Boolean
        get() = NSProcessInfo.processInfo.environment["IS_DEBUG"] == "true"
}

actual fun getBuildConfig(): BuildConfig = BuildConfigIos()