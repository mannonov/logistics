package com.myapp.logistics.repository

import com.myapp.logistics.model.Load

interface LoadsRepository {
    suspend fun getNewLoads(result: (result: List<Load>) -> Unit)
    suspend fun getActiveLoads(result: (result: List<Load>) -> Unit)
    suspend fun getCompletedLoads(result: (result: List<Load>) -> Unit)
}
