package com.myapp.logistics.repository

import com.myapp.logistics.model.Driver

interface SignInRepository {

    suspend fun getDriverData(userName: String, password: String, result: (result: Driver) -> Unit)
}
