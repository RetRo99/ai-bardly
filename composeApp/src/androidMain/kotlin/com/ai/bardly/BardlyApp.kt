package com.ai.bardly

import android.app.Application
import com.ai.bardly.di.initKoin
import org.koin.android.ext.koin.androidContext

class BardlyApp : Application() {
    override fun onCreate() {
        super.onCreate()
        initKoin {
            androidContext(this@BardlyApp)
        }
    }
}
