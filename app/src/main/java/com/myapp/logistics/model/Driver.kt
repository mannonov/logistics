package com.myapp.logistics.model

import com.myapp.logistics.util.Constants

data class Driver(
    var id: String? = null,
    var fio: String? = null,
    var car: String? = null,
    var phoneNumber: String? = null
) {
    fun getHashMap(): HashMap<String, Any> {
        return hashMapOf(
            Constants.FIO to fio.toString(),
            Constants.CAR to car.toString(),
            Constants.PHONE_NUMBER to phoneNumber.toString()
        )
    }
}
