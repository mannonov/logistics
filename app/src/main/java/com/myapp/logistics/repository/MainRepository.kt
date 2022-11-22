package com.myapp.logistics.repository

import com.myapp.logistics.map.AbstractPosition
import com.myapp.logistics.model.Driver

interface MainRepository {

    suspend fun getLastKnownLocation(): AbstractPosition?

    suspend fun getLastLocation(): Result<AbstractPosition>

    suspend fun sendDriverLastLocation(driver: Driver, lastLocation: AbstractPosition)
}
