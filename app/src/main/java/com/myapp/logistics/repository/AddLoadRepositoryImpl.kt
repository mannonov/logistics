package com.myapp.logistics.repository

import com.google.firebase.firestore.FirebaseFirestore
import com.myapp.logistics.model.Load
import com.myapp.logistics.util.Constants
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AddLoadRepositoryImpl @Inject constructor(private val firestore: FirebaseFirestore) : AddLoadRepository {

    override suspend fun addLoadFirebase(load: Load, result: (boolean: Boolean) -> Unit) {
        firestore.collection(Constants.LOADS).add(load.getHashMap()).addOnSuccessListener {
            result(true)
        }.addOnFailureListener {
            result(false)
        }
    }
}
