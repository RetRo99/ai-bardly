package com.ai.bardly

import android.app.Application
import com.ai.bardly.di.initKoin

class BardlyApp : Application() {
    override fun onCreate() {
        super.onCreate()
        initKoin()
    }
}
