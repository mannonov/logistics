package com.myapp.logistics.model

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.myapp.logistics.util.Constants
import java.io.Serializable
import java.lang.reflect.Type

data class Load(
    var id: String? = null,
    var aPoint: Point? = null,
    var bPoint: Point? = null,
    var customer: String? = null,
    var deadline: String? = null,
    var description: String? = null,
    var status: String? = "new",
    var attachedDriverId: String? = null,
    var acceptedTime: String? = null,
    var completedTime: String? = null
) : Serializable {

    fun getHashMap(): HashMap<String, Any> {
        return hashMapOf(
            Constants.A_POINT to gson.toJson(aPoint, pointType),
            Constants.B_POINT to gson.toJson(bPoint, pointType),
            Constants.CUSTOMER to customer.toString(),
            Constants.DEADLINE to deadline.toString(),
            Constants.DESCRIPTION to description.toString(),
            Constants.STATUS to status.toString()
        )
    }

    fun getAcceptHashMap(newStatus: String, driverId: String): HashMap<String, Any> {
        return hashMapOf(
            Constants.A_POINT to gson.toJson(aPoint, pointType),
            Constants.B_POINT to gson.toJson(bPoint, pointType),
            Constants.CUSTOMER to customer.toString(),
            Constants.DEADLINE to deadline.toString(),
            Constants.DESCRIPTION to description.toString(),
            Constants.STATUS to newStatus,
            Constants.DRIVER_ID to driverId,
            Constants.ACCEPTED_TIME to System.currentTimeMillis().toString()
        )
    }

    fun getFinishHashMap(newStatus: String): HashMap<String, Any> {
        return hashMapOf(
            Constants.A_POINT to gson.toJson(aPoint, pointType),
            Constants.B_POINT to gson.toJson(bPoint, pointType),
            Constants.CUSTOMER to customer.toString(),
            Constants.DEADLINE to deadline.toString(),
            Constants.DESCRIPTION to description.toString(),
            Constants.STATUS to newStatus,
            Constants.DRIVER_ID to attachedDriverId.toString(),
            Constants.ACCEPTED_TIME to acceptedTime.toString(),
            Constants.COMPLETED_TIME to System.currentTimeMillis().toString()
        )
    }

    companion object {
        private val gson = Gson()
        private val pointType: Type = object : TypeToken<Point>() {}.type
        fun toObject(hashMap: Map<String, Any>, id: String): Load {
            val load = Load()
            load.aPoint = gson.fromJson(hashMap[Constants.A_POINT].toString(), pointType)
            load.bPoint = gson.fromJson(hashMap[Constants.B_POINT].toString(), pointType)
            load.customer = hashMap[Constants.CUSTOMER].toString()
            load.deadline = hashMap[Constants.DEADLINE].toString()
            load.description = hashMap[Constants.DESCRIPTION].toString()
            load.status = hashMap[Constants.STATUS].toString()
            load.id = id
            load.attachedDriverId = hashMap[Constants.DRIVER_ID].toString()
            load.acceptedTime = hashMap[Constants.ACCEPTED_TIME].toString()
            load.completedTime = hashMap[Constants.COMPLETED_TIME].toString()
            return load
        }
    }
}
