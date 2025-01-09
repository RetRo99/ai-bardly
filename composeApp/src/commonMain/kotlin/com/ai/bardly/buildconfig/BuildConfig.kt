package com.ai.bardly.buildconfig

interface BuildConfig {
    val isDebug: Boolean
}

expect fun getBuildConfig(): BuildConfig