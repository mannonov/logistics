package com.myapp.logistics.repository

import com.google.firebase.firestore.FirebaseFirestore
import com.myapp.logistics.model.Driver
import com.myapp.logistics.util.Constants
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AddDriverRepositoryImpl @Inject constructor(private val firebaseFirestore: FirebaseFirestore) : AddDriverRepository {
    override suspend fun addLoadFirebase(driver: Driver, result: (boolean: Boolean) -> Unit) {
        firebaseFirestore.collection(Constants.DRIVERS).add(driver.getHashMap()).addOnSuccessListener {
            result(true)
        }.addOnFailureListener {
            result(false)
        }
    }
}
