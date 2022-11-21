package com.myapp.logistics.repository

import com.myapp.logistics.model.Driver

interface DriversRepository {

    suspend fun getDrivers(result: (result: List<Driver>) -> Unit)
}
