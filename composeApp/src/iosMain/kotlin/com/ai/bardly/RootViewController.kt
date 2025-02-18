package com.ai.bardly

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.ui.Modifier
import androidx.compose.ui.window.ComposeUIViewController
import com.ai.bardly.app.App
import com.arkivanov.decompose.ExperimentalDecomposeApi
import com.arkivanov.decompose.extensions.compose.stack.animation.predictiveback.PredictiveBackGestureIcon
import com.arkivanov.decompose.extensions.compose.stack.animation.predictiveback.PredictiveBackGestureOverlay
import com.arkivanov.essenty.backhandler.BackDispatcher
import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.crashlytics.crashlytics
import dev.gitlive.firebase.initialize
import me.tatarka.inject.annotations.Inject
import platform.UIKit.UIViewController

typealias RootViewController = () -> UIViewController

@OptIn(ExperimentalDecomposeApi::class)
@Inject
fun RootViewController(app: App, backDispatcher: BackDispatcher): UIViewController {
    return ComposeUIViewController {
        PredictiveBackGestureOverlay(
            backDispatcher = backDispatcher,
            backIcon = { progress, _ ->
                PredictiveBackGestureIcon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    progress = progress,
                )
            },
            modifier = Modifier.fillMaxSize()
        ) {
            app()
        }
    }
}

fun initFirebase() {
    Firebase.initialize()
    Firebase.crashlytics.setCrashlyticsCollectionEnabled(true)
}
