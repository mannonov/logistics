package com.myapp.logistics.repository

import com.myapp.logistics.model.Load

interface AddLoadRepository {

    suspend fun addLoadFirebase(load: Load, result: (boolean: Boolean) -> Unit)
}
