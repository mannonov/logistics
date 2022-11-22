package com.myapp.logistics.app

import android.app.Application
import android.content.Context
import com.here.sdk.core.engine.SDKNativeEngine
import com.here.sdk.core.engine.SDKOptions
import com.here.sdk.core.errors.InstantiationErrorException
import com.myapp.logistics.R
import com.myapp.logistics.util.RemoteConfigUtils
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class LogisticsApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        RemoteConfigUtils.init()
        initializeHERESDK()
    }
    private fun initializeHERESDK() {
        val accessKeyID = getString(R.string.here_sdk_api_key_value)
        val accessKeySecret = getString(R.string.here_sdk_api_key_value)
        val options = SDKOptions(accessKeyID, accessKeySecret)
        try {
            val context: Context = this
            SDKNativeEngine.makeSharedInstance(context, options)
        } catch (e: InstantiationErrorException) {
            throw RuntimeException("Initialization of HERE SDK failed: " + e.error.name)
        }
    }
}
