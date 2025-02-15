package com.ai.bardly

import androidx.compose.runtime.remember
import androidx.compose.ui.window.ComposeUIViewController
import com.ai.bardly.navigation.root.application.DefaultDecomposeRoot
import com.arkivanov.decompose.DefaultComponentContext
import com.arkivanov.essenty.lifecycle.ApplicationLifecycle
import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.crashlytics.crashlytics
import dev.gitlive.firebase.initialize

fun MainViewController() = ComposeUIViewController {
    val applicationComponent = remember {
        DefaultDecomposeRoot(DefaultComponentContext(ApplicationLifecycle()))
    }
    App(applicationComponent)
}

fun initFirebase() {
    Firebase.initialize()
    Firebase.crashlytics.setCrashlyticsCollectionEnabled(true)
}