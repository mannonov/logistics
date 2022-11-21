package com.myapp.logistics.repository

import com.google.firebase.firestore.FirebaseFirestore
import com.myapp.logistics.model.Load
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DriverLoadsRepositoryImpl @Inject constructor(private val firestore: FirebaseFirestore) : DriverLoadsRepository {
    override suspend fun getNewLoads(id: String, result: (result: List<Load>) -> Unit) {
        TODO("Not yet implemented")
    }

    override suspend fun getActiveLoads(id: String, result: (result: List<Load>) -> Unit) {
        TODO("Not yet implemented")
    }

    override suspend fun getCompletedLoads(id: String, result: (result: List<Load>) -> Unit) {
        TODO("Not yet implemented")
    }
}