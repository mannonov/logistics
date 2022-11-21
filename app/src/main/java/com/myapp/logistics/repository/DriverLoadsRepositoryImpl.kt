package com.myapp.logistics.repository

import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import com.myapp.logistics.model.Load
import com.myapp.logistics.util.Constants
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DriverLoadsRepositoryImpl @Inject constructor(private val firestore: FirebaseFirestore) : DriverLoadsRepository {
    override suspend fun getNewLoads(result: (result: List<Load>) -> Unit) {
        val loads = ArrayList<Load>()
        firestore.collection(Constants.LOADS).get().addOnCompleteListener { task ->
            task.result.documents.forEach { documentSnapshot ->
                documentSnapshot.data?.get(Constants.STATUS)?.let {
                    Log.d("sfbhjdsahj", "getNewLoads: $it")
                    if (it.toString() == Constants.NEW) {
                        val load = Load.toObject(documentSnapshot.data!!, documentSnapshot.id)
                        loads.add(load)
                        Log.d("sfbhjdsahj", "getNewLoads: $load")
                    }
                }
            }
            result(loads)
        }
    }

    override suspend fun getActiveLoads(id: String, result: (result: List<Load>) -> Unit) {
        val loads = ArrayList<Load>()
        firestore.collection(Constants.LOADS).get().addOnCompleteListener { task ->
            task.result.documents.forEach { documentSnapshot ->
                documentSnapshot.data?.get(Constants.STATUS)?.let {
                    Log.d("sfbhjdsahj", "getNewLoads: $it")
                    if (it.toString() == Constants.ACTIVE) {
                        val load = Load.toObject(documentSnapshot.data!!, documentSnapshot.id)
                        if (load.attachedDriverId.toString() == id) {
                            loads.add(load)
                        }
                        Log.d("sfbhjdsahj", "getNewLoads: $load")
                    }
                }
            }
            result(loads)
        }
    }

    override suspend fun getCompletedLoads(id: String, result: (result: List<Load>) -> Unit) {
        val loads = ArrayList<Load>()
        firestore.collection(Constants.LOADS).get().addOnCompleteListener { task ->
            task.result.documents.forEach { documentSnapshot ->
                documentSnapshot.data?.get(Constants.COMPLETED)?.let {
                    Log.d("sfbhjdsahj", "getNewLoads: $it")
                    if (it.toString() == Constants.ACTIVE) {
                        val load = Load.toObject(documentSnapshot.data!!, documentSnapshot.id)
                        if (load.attachedDriverId.toString() == id) {
                            loads.add(load)
                        }
                        Log.d("sfbhjdsahj", "getNewLoads: $load")
                    }
                }
            }
            result(loads)
        }
    }
}
