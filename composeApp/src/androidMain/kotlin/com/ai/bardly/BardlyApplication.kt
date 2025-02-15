package com.ai.bardly

import android.app.Application
import com.ai.bardly.di.AppComponent
import com.ai.bardly.di.ApplicationModule
import com.ai.bardly.di.create
import com.ai.bardly.di.initKoin
import org.koin.android.ext.koin.androidContext
import org.koin.ksp.generated.module

class BardlyApplication : Application() {
    private lateinit var component: AppComponent

    override fun onCreate() {
        super.onCreate()
        component = AppComponent::class.create(this)
        initKoin(ApplicationModule().module) {
            androidContext(this@BardlyApplication)
        }
    }

    fun getApplicationComponent() = component
}
