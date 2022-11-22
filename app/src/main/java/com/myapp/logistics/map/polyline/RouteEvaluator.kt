package com.myapp.logistics.map.polyline

import android.animation.TypeEvaluator
import com.google.android.gms.maps.model.LatLng

/**
 * Created by amal.chandran on 22/12/16.
 */
class RouteEvaluator : TypeEvaluator<LatLng> {
    override fun evaluate(t: Float, startPoint: LatLng, endPoint: LatLng): LatLng {
        val lat = startPoint.latitude + t * (endPoint.latitude - startPoint.latitude)
        val lng = startPoint.longitude + t * (endPoint.longitude - startPoint.longitude)
        return LatLng(lat, lng)
    }
}
