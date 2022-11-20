package com.myapp.logistics.model

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.myapp.logistics.util.Constants
import java.lang.reflect.Type

data class Load(
    var aPoint: Point? = null,
    var bPoint: Point? = null,
    var customer: String? = null,
    var deadline: String? = null,
    var description: String? = null
) {

    private val gson = Gson()
    private val pointType: Type = object : TypeToken<Point>() {}.type

    fun getHashMap(): HashMap<String, Any> {
        return hashMapOf(
            Constants.A_POINT to gson.toJson(aPoint, pointType),
            Constants.B_POINT to gson.toJson(bPoint, pointType),
            Constants.CUSTOMER to customer.toString(),
            Constants.DEADLINE to deadline.toString(),
            Constants.DESCRIPTION to description.toString()
        )
    }
}
