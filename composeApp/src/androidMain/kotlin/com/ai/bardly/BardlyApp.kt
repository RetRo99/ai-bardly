package com.ai.bardly

import android.app.Application
import com.ai.bardly.di.ApplicationModule
import com.ai.bardly.di.initKoin
import org.koin.android.ext.koin.androidContext
import org.koin.ksp.generated.module

class BardlyApp : Application() {
    override fun onCreate() {
        super.onCreate()
        initKoin(ApplicationModule().module) {
            androidContext(this@BardlyApp)
        }
    }
}
