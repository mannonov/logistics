package com.myapp.logistics.repository

import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import com.myapp.logistics.model.Driver
import com.myapp.logistics.util.Constants
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DriversRepositoryImpl @Inject constructor(private val firestore: FirebaseFirestore) : DriversRepository {
    override suspend fun getDrivers(result: (result: List<Driver>) -> Unit) {
        val drivers = ArrayList<Driver>()
        firestore.collection(Constants.DRIVERS).get().addOnCompleteListener { task ->
            task.result.documents.forEach { documentSnapshot ->
                val driver = documentSnapshot.toObject(Driver::class.java)
                driver?.id = documentSnapshot.id
                if (driver != null) {
                    drivers.add(driver)
                }
                Log.d("driversres", "getDrivers: $driver")
            }
            result(drivers)
        }
    }
}
