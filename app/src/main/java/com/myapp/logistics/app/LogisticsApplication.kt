package com.myapp.logistics.app

import android.app.Application
import com.myapp.logistics.util.RemoteConfigUtils
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class LogisticsApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        RemoteConfigUtils.init()
    }
}
