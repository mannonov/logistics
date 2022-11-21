package com.myapp.logistics.app

import android.app.Application
import android.content.Context
import android.util.Log
import com.here.sdk.core.engine.SDKNativeEngine
import com.here.sdk.core.engine.SDKOptions
import com.here.sdk.core.errors.InstantiationErrorException
import com.myapp.logistics.util.RemoteConfigUtils
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class LogisticsApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        RemoteConfigUtils.init()
    }
    private fun initializeHERESDK() {
        val accessKeyID = "MD54oUgWn8rzz8sK41k1mg"
        val accessKeySecret = "oyDIIjuI7z7a7kH5fgCCOUnK4_3U_u8UJ_0KzaraeeBTi_nXekBYhTfsv615B-lOMnMgXkPApEzqhUF3VEo7Sw"
        val options = SDKOptions(accessKeyID, accessKeySecret)
        try {
            val context: Context = this
            SDKNativeEngine.makeSharedInstance(context, options)
        } catch (e: InstantiationErrorException) {
            throw RuntimeException("Initialization of HERE SDK failed: " + e.error.name)
        }
    }
}
