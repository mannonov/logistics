package com.myapp.logistics.map

import android.content.Context
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.Projection
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import com.myapp.logistics.map.camera.*
import com.myapp.logistics.map.marker.AbstractMarker
import com.myapp.logistics.map.marker.AbstractMarkerOptions
import com.myapp.logistics.map.polyline.AbstractPolyline
import com.myapp.logistics.map.polyline.AbstractPolylineOptions

abstract class AbstractMap {
    companion object {
        val REASON_GESTURE = 1
        val REASON_API_ANIMATION = 2
        val REASON_DEVELOPER_ANIMATION = 3
        val REASON_MY_LOCATION_CLICK = 4
        val REASON_MARKER_CLICK = 5
    }

    var mapType: Int = 0

    abstract fun addMarker(marker: AbstractMarkerOptions<Any>): AbstractMarker

    abstract fun moveCamera(coords: AbstractCameraPosition, animate: Boolean)

    abstract fun moveCamera(position: AbstractPosition, animate: Boolean)
    abstract fun moveCamera(
        position: AbstractPosition,
        animate: Boolean,
        cancelableCallback: AnimationCallback
    )

    abstract fun moveCamera(lat: Double, lng: Double, animate: Boolean)
    abstract fun moveCamera(lat: Double, lng: Double, animate: Boolean, zoom: Float)
    abstract fun moveCamera(position: AbstractPosition, zoom: Float, animate: Boolean)
    abstract fun moveCamera(
        position: AbstractPosition,
        zoom: Float,
        cancelableCallback: AnimationCallback?
    )

    abstract fun moveCamera(zoom: Float, animate: Boolean)
    abstract fun moveCamera(
        bounds: AbstractBounds,
        padding: Int,
        animate: Boolean,
        cancelableCallback: AnimationCallback?
    )

    abstract fun moveCamera(
        bounds: AbstractBounds,
        width: Int,
        height: Int,
        padding: Int,
        animate: Boolean,
        cancelableCallback: AnimationCallback?
    )

    abstract fun moveCamera(
        to: LatLng,
        from: LatLng,
        padding: Int,
        animate: Boolean,
        cancelableCallback: AnimationCallback?
    )

    abstract fun moveCamera(
        points: ArrayList<Point>,
        padding: Int,
        animate: Boolean,
        cancelableCallback: AnimationCallback?
    )

    abstract fun moveCamera(
        builder: LatLngBounds.Builder,
        padding: Int,
        animate: Boolean,
        cancelableCallback: AnimationCallback?
    )

    abstract fun getCameraPosition(): AbstractCameraPosition
    abstract fun getPosition(): AbstractPosition
    abstract fun getZoom(): Float

    abstract fun addPolyline(polylineOptions: AbstractPolylineOptions): AbstractPolyline

    abstract fun setPadding(left: Int, top: Int, right: Int, bottom: Int)
    abstract fun stopAnimation()

    abstract fun setScrollGesturesEnabled(enabled: Boolean)

    abstract fun setOnCameraIdleListener(listener: CameraEventListener?)

    abstract fun setOnCameraMoveListener(listener: CameraEventListener?)

    abstract fun setOnCameraMoveStartedListener(listener: CameraStartMovingListener?)

    abstract fun setOnCameraMoveCanceledListener(listener: CameraEventListener?)

    abstract fun setOnMapClickListener(listener: GoogleMap.OnMapClickListener)

    abstract fun setMyLocationIndicator(enabled: Boolean,context: Context)
    abstract fun setZoomGesturesEnabled(enabled: Boolean)
    abstract fun isMyLocationIndicator(): Boolean
    abstract fun setMapTypeIsSat(isSta: Boolean)
    abstract fun setOnMarkerClickListener(listener: MarkerClickListener)
    abstract fun zoomBy(d: Float)
    abstract fun zoomBy(d: Float, finish: Runnable, onCancel: Runnable)
    abstract fun setMaxZoomLevel(d: Float)
    abstract fun setMinZoomLevel(d: Float)
    abstract fun animateCamera(position: AbstractPosition, zoom: Float, animate: Boolean)

    abstract fun setOnMapLoadedCallback(var1: GoogleMap.OnMapLoadedCallback)

    abstract fun onResume()
    abstract fun onPause()

    abstract fun onDestroy()
    abstract fun onLowMemory()

    abstract fun projection(): Projection

    abstract fun darkMode(boolean: Boolean, context: Context)
}
