package com.ai.bardly

import android.app.Application
import com.ai.bardly.di.AppComponent
import com.ai.bardly.di.create
import com.mmk.kmpauth.google.GoogleAuthCredentials
import com.mmk.kmpauth.google.GoogleAuthProvider

class BardlyApplication : Application() {
    private lateinit var component: AppComponent

    override fun onCreate() {
        super.onCreate()
        component = AppComponent::class.create(this)
        // TODO move somewhere else, do this on ios
        GoogleAuthProvider.create(GoogleAuthCredentials(serverId = "202431209061-0ku3miec01ehdhkp84jcpmp6lfbpefeq.apps.googleusercontent.com"))
    }

    fun getApplicationComponent() = component
}
