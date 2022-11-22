package com.myapp.logistics.repository

import com.here.sdk.routing.Route
import com.myapp.logistics.map.AbstractPosition
import com.myapp.logistics.util.Outcome

class LoadInfoRepositoryImpl : LoadInfoRepository {
    override suspend fun getRoute(a: AbstractPosition, b: AbstractPosition, outcome: (Outcome<Route>) -> Unit) {
        TODO("Not yet implemented")
    }
}