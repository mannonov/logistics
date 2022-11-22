package com.myapp.logistics.repository

import android.annotation.SuppressLint
import com.google.android.gms.location.FusedLocationProviderClient
import com.myapp.logistics.map.AbstractPosition
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

@Singleton
class MainRepositoryImpl @Inject constructor(private val fusedLocationClient: FusedLocationProviderClient) : MainRepository {
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
}
