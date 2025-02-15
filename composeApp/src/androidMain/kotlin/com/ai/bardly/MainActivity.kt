package com.ai.bardly

import android.graphics.Color
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.LaunchedEffect
import com.ai.bardly.di.ActivityComponent
import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.initialize

class MainActivity : ComponentActivity() {
    private lateinit var component: ActivityComponent

    override fun onCreate(savedInstanceState: Bundle?) {
        component = ActivityComponent.create(this)
        super.onCreate(savedInstanceState)
        Firebase.initialize(this)
        setContent {
            LaunchedEffect(isSystemInDarkTheme()) {
                enableEdgeToEdge(
                    statusBarStyle = SystemBarStyle.light(
                        Color.LTGRAY, Color.LTGRAY
                    ),
                    navigationBarStyle = SystemBarStyle.light(
                        Color.WHITE, Color.WHITE
                    )
                )
            }
            component.app()
        }
    }
}
