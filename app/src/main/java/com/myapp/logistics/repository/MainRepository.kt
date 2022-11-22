package com.myapp.logistics.repository

import com.myapp.logistics.map.AbstractPosition

interface MainRepository {

    suspend fun getLastKnownLocation(): AbstractPosition?

    suspend fun getLastLocation(): Result<AbstractPosition>
}
