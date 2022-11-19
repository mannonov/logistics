package com.myapp.logistics.util

import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings

object RemoteConfigUtils {

    private const val ADMIN_LOGIN = "admin_login"
    private const val ADMIN_PASSWORD = "admin_password"

    private val DEFAULTS: HashMap<String, Any> = hashMapOf(
        ADMIN_LOGIN to "admin",
        ADMIN_PASSWORD to "admin"
    )

    private lateinit var remoteConfig: FirebaseRemoteConfig

    fun init() {
        remoteConfig = getFirebaseRemoteConfig()
    }

    private fun getFirebaseRemoteConfig(): FirebaseRemoteConfig {
        val remoteConfig = FirebaseRemoteConfig.getInstance()

        val configSettings = FirebaseRemoteConfigSettings.Builder()
            .setMinimumFetchIntervalInSeconds(0)
            .build()

        remoteConfig.setConfigSettingsAsync(configSettings)
        remoteConfig.setDefaultsAsync(DEFAULTS)

        remoteConfig.fetchAndActivate().addOnCompleteListener {
        }

        return remoteConfig
    }

    fun getAdminLogin(): String = remoteConfig.getString(ADMIN_LOGIN)
    fun getAdminPassword(): String = remoteConfig.getString(ADMIN_PASSWORD)
}
