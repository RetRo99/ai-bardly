package com.ai.bardly

import android.graphics.Color
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.LaunchedEffect
import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.initialize

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Firebase.initialize(this)
        setContent {
            LaunchedEffect(isSystemInDarkTheme()) {
                enableEdgeToEdge(
                    statusBarStyle = SystemBarStyle.light(
                        Color.GRAY, Color.GRAY
                    ),
                    navigationBarStyle = SystemBarStyle.light(
                        Color.WHITE, Color.WHITE
                    )
                )
            }
            App()
        }
    }
}
