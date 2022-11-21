package com.myapp.logistics.repository

import com.myapp.logistics.model.Driver

interface AddDriverRepository {

    suspend fun addLoadFirebase(driver: Driver, result: (boolean: Boolean) -> Unit)
}
