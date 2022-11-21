package com.myapp.logistics.repository

import com.google.firebase.firestore.FirebaseFirestore
import com.myapp.logistics.model.Driver
import com.myapp.logistics.util.Constants
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SignInRepositoryImpl @Inject constructor(private val firestore: FirebaseFirestore) : SignInRepository {
    override suspend fun getDriverData(userName: String, password: String, result: (result: Driver) -> Unit) {
        firestore.collection(Constants.DRIVERS).get().addOnCompleteListener { task ->
            task.result.documents.forEach { documentSnapshot ->
                val driver = documentSnapshot.toObject(Driver::class.java)
                if (driver?.userName == userName && driver.password == password) {
                    result(driver)
                }
            }
        }
    }
}
