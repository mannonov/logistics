package com.myapp.logistics.model

import com.myapp.logistics.util.Constants

data class Load(
    var aPoint: Point? = null,
    var bPoint: Point? = null,
    var customer: String? = null,
    var deadline: String? = null,
    var description: String? = null
) {



    fun getHashMap(): HashMap<String, Any> {
        return hashMapOf(
            Constants.A_POINT to aPoint.toString(),
            Constants.B_POINT to bPoint.toString(),
            Constants.CUSTOMER to customer.toString(),
            Constants.DEADLINE to deadline.toString(),
            Constants.DESCRIPTION to description.toString()
        )
    }
}
