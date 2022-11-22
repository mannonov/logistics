package com.myapp.logistics.repository

import android.util.Log
import com.here.sdk.core.GeoCoordinates
import com.here.sdk.routing.Route
import com.here.sdk.routing.RoutingEngine
import com.here.sdk.routing.TruckOptions
import com.here.sdk.routing.Waypoint
import com.myapp.logistics.map.AbstractPosition
import com.myapp.logistics.util.Outcome
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LoadInfoRepositoryImpl @Inject constructor() : LoadInfoRepository {
    override suspend fun getRoute(a: AbstractPosition, b: AbstractPosition, outcome: (Outcome<Route>) -> Unit) {
        RoutingEngine().calculateRoute(listOf(Waypoint(GeoCoordinates(a.lat, a.lng)), Waypoint(GeoCoordinates(b.lat, b.lng))), TruckOptions()) { routingError, routes ->
            if (routingError == null) {
                routes?.get(0)?.let {
                    outcome(Outcome.success(it))
                }
            } else {
                Log.d("routeFail", "getRoute: data doesn't come $routingError")
            }
        }
    }
}
