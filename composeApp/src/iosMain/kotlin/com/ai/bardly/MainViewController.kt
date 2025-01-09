package com.ai.bardly

import androidx.compose.ui.window.ComposeUIViewController
import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.crashlytics.crashlytics
import dev.gitlive.firebase.initialize

fun MainViewController() = ComposeUIViewController { App() }

fun init() {
    Firebase.initialize()
    Firebase.crashlytics.setCrashlyticsCollectionEnabled(true)
}