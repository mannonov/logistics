package com.myapp.logistics.util

import android.content.SharedPreferences
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.myapp.logistics.model.Driver
import java.lang.reflect.Type
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LogisticsPref @Inject constructor(private val sharedPreferences: SharedPreferences) {

    private val gson = Gson()
    private val driverType: Type = object : TypeToken<Driver>() {}.type

    var userType: Int
        get() = sharedPreferences.getInt(USER_TYPE, 0)
        set(value) = sharedPreferences.edit().putInt(USER_TYPE, value).apply()

    var driver: Driver
        get() = gson.fromJson(sharedPreferences.getString(DRIVER, gson.toJson(Driver(), driverType)), driverType)
        set(value) = sharedPreferences.edit().putString(DRIVER, gson.toJson(value)).apply()

    companion object {
        const val USER_TYPE = "user_type"
        const val DRIVER = "driver"
    }
}
