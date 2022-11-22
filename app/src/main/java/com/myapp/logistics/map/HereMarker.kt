package com.myapp.logistics.map

import android.graphics.Point
import com.here.sdk.mapviewlite.MapMarker
import com.here.sdk.mapviewlite.MapViewLite
import com.myapp.logistics.map.marker.AbstractMarker
import com.myapp.logistics.map.marker.AbstractMarkerOptions

class HereMarker constructor(marker: AbstractMarkerOptions<Any>, private val mapView: MapViewLite) : AbstractMarker() {

//    init {
//        val icon: MapImage
//        val markerIcon = marker.icon
//        icon = applyIcon(markerIcon!!)
//        mapView.mapScene.addMapMarker(
//            MapMarker(GeoCoordinates(marker.latLng.lat, marker.latLng.lng)).apply {
//                addImage(
//                    icon,
//                    MapMarkerImageStyle().apply {
//                        anchorPoint = Anchor2D(marker.anchorX?.toDouble() ?: 0.0, marker.anchorY?.toDouble() ?: 0.0)
//                        setRotation(marker.latLng.rotation)
//                    }
//                )
//                isVisible = true
//            }
//        )
//    }

//    fun applyIcon(markerIcon: Any): MapImage {
//        val icon: MapImage = when (markerIcon) {
//            is Int -> {
//                MapImageFactory.fromResource(MyTrkrApplication.context!!.applicationContext.resources, markerIcon)
//            }
//            is Bitmap -> {
//                MapImageFactory.fromBitmap(markerIcon)
//            }
//            else -> {
//                MapImageFactory.fromResource(MyTrkrApplication.context!!.applicationContext.resources, R.drawable.ic_mark_green)
//            }
//        }
//        return icon
//    }

    override fun remove() {}

    override fun getRotation(): Float {
        TODO("Not yet implemented")
    }

    override fun getPosition(): AbstractPosition {
        TODO("Not yet implemented")
    }

    override fun setPosition(newPosition: AbstractPosition) {
        TODO("Not yet implemented")
    }

    override fun setRotation(newRotation: Float) {
        TODO("Not yet implemented")
    }

    override fun setIcon(icon: Any): Any {
        TODO("Not yet implemented")
    }

    override fun getScreenPosition(map: AbstractMap): Point? {
        TODO("Not yet implemented")
    }

    override fun showInfoWindow() {
        TODO("Not yet implemented")
    }

    override fun hideInfoWindow() {
        TODO("Not yet implemented")
    }

    override fun setVisible(visible: Boolean) {
        (marker as MapMarker).isVisible = false
    }
}
