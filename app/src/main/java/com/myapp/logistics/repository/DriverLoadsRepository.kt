package com.myapp.logistics.repository

import com.myapp.logistics.model.Load

interface DriverLoadsRepository {
    suspend fun getNewLoads(result: (result: List<Load>) -> Unit)
    suspend fun getActiveLoads(id: String, result: (result: List<Load>) -> Unit)
    suspend fun getCompletedLoads(id: String, result: (result: List<Load>) -> Unit)
}
