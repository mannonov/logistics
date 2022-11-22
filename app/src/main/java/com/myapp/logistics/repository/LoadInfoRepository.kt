package com.myapp.logistics.repository

import com.here.sdk.routing.Route
import com.myapp.logistics.map.AbstractPosition
import com.myapp.logistics.util.Outcome

interface LoadInfoRepository {

    suspend fun getRoute(a: AbstractPosition, b: AbstractPosition, outcome: ((Outcome<Route>) -> Unit))
}
