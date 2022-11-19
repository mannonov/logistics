package com.myapp.logistics.util

import android.content.SharedPreferences
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LogisticsPref @Inject constructor(private val sharedPreferences: SharedPreferences) {

    var userType: Int
        get() = sharedPreferences.getInt(USER_TYPE, 0)
        set(value) = sharedPreferences.edit().putInt(USER_TYPE, value).apply()

    companion object {
        const val USER_TYPE = "user_type"
    }
}
