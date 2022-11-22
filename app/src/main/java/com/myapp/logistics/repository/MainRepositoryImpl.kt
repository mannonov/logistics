package com.myapp.logistics.repository

import android.annotation.SuppressLint
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.firebase.firestore.FirebaseFirestore
import com.myapp.logistics.map.AbstractPosition
import com.myapp.logistics.model.Driver
import com.myapp.logistics.util.Constants
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

@Singleton
class MainRepositoryImpl @Inject constructor(private val fusedLocationClient: FusedLocationProviderClient, private val firestore: FirebaseFirestore) : MainRepository {
    @SuppressLint("MissingPermission")
    override suspend fun getLastKnownLocation(): AbstractPosition? {
        return suspendCoroutine { continuation ->
            fusedLocationClient.lastLocation.addOnSuccessListener {
                if (it != null) {
                    continuation.resume(AbstractPosition(it.latitude, it.longitude))
                } else continuation.resume(null)
            }
        }
    }

    @SuppressLint("MissingPermission")
    override suspend fun getLastLocation(): Result<AbstractPosition> {
        return suspendCoroutine { continuation ->
            fusedLocationClient.lastLocation.addOnSuccessListener {
                if (it != null) {
                    continuation.resume(Result.success(AbstractPosition(it.latitude, it.longitude)))
                } else continuation.resume(Result.failure(Exception("null")))
            }.addOnFailureListener {
                continuation.resume(Result.failure(it))
            }
        }
    }

    override suspend fun sendDriverLastLocation(driver: Driver, lastLocation: AbstractPosition) {
        val docRef = firestore.collection(Constants.DRIVERS).document(driver.id.toString())
        val updatedDriver = driver.getLocationUpdateHashMap(lastLocation.lat, lastLocation.lng)
        docRef.update(updatedDriver)
    }
}
