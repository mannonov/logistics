package com.myapp.logistics.map

import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.LatLng
import com.myapp.logistics.map.camera.AbstractBounds
import com.myapp.logistics.map.polyline.AbstractPolyline
import com.myapp.logistics.map.polyline.AbstractPolylineOptions
import com.myapp.logistics.map.polyline.MapAnimator

class GooglePolyline(var aPolyline: AbstractPolylineOptions, map: GoogleMap) : AbstractPolyline() {

    init {
        MapAnimator.instance?.animateRoute(
            map,
            aPolyline.points.map { LatLng(it.lat, it.lng) },
            aPolyline.width.toInt()
        )
    }

    override fun getBounds(): AbstractBounds {
        val bounds = AbstractBounds()
        aPolyline.points.forEach {
            bounds.include(it)
        }
        return bounds
    }

    override fun remove() {
        MapAnimator.instance?.stopAndRemovePolyLine()
    }
}
