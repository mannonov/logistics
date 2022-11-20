package com.myapp.logistics.repository

import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import com.myapp.logistics.model.Load
import com.myapp.logistics.util.Constants
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LoadsRepositoryImpl @Inject constructor(private val firebaseFirestore: FirebaseFirestore) : LoadsRepository {
    override suspend fun getNewLoads(result: (result: List<Load>) -> Unit) {
        val loads = ArrayList<Load>()
        firebaseFirestore.collection(Constants.LOADS).get().addOnCompleteListener { task ->
            task.result.documents.forEach { documentSnapshot ->
                documentSnapshot.data?.get(Constants.STATUS)?.let {
                    Log.d("sfbhjdsahj", "getNewLoads: $it")
                    if (it.toString() == Constants.NEW) {
                        val load = Load.toObject(documentSnapshot.data!!)
                        loads.add(load)
                        Log.d("sfbhjdsahj", "getNewLoads: $load")
                    }
                }
            }
            result(loads)
        }
    }

    override suspend fun getActiveLoads(result: (result: List<Load>) -> Unit) {
        TODO("Not yet implemented")
    }

    override suspend fun getCompletedLoads(result: (result: List<Load>) -> Unit) {
        TODO("Not yet implemented")
    }
}
