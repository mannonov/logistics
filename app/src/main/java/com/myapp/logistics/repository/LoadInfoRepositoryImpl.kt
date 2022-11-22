package com.myapp.logistics.repository

import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import com.here.sdk.core.GeoCoordinates
import com.here.sdk.routing.Route
import com.here.sdk.routing.RoutingEngine
import com.here.sdk.routing.TruckOptions
import com.here.sdk.routing.Waypoint
import com.myapp.logistics.map.AbstractPosition
import com.myapp.logistics.model.Load
import com.myapp.logistics.util.Constants
import com.myapp.logistics.util.Outcome
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LoadInfoRepositoryImpl @Inject constructor(private val firestore: FirebaseFirestore) : LoadInfoRepository {
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

    override suspend fun acceptOrder(load: Load, driverId: String, result: (boolean: Boolean) -> Unit) {
        val docRef = firestore.collection(Constants.LOADS).document(load.id.toString())
        val updatedLoad: HashMap<String, Any> = load.getAcceptHashMap(newStatus = Constants.ACTIVE, driverId = driverId)
        docRef.update(updatedLoad).addOnCompleteListener {
            result(true)
        }.addOnFailureListener {
            result(false)
        }
    }
}
