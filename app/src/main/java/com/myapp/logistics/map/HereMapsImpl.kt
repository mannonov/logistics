package com.myapp.logistics.map

import android.content.Context
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.Projection
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import com.here.sdk.core.GeoCoordinates
import com.here.sdk.mapviewlite.MapViewLite
import com.myapp.logistics.map.camera.*
import com.myapp.logistics.map.marker.AbstractMarker
import com.myapp.logistics.map.marker.AbstractMarkerOptions
import com.myapp.logistics.map.polyline.AbstractPolyline
import com.myapp.logistics.map.polyline.AbstractPolylineOptions

class HereMapsImpl(private val mapView: MapViewLite) : AbstractMap() {

    private var cameraStartMovingListener: CameraStartMovingListener? = null

    init {
        mapView.camera.addObserver {
            cameraStartMovingListener?.onEvent(0)
        }
    }

    override fun addMarker(marker: AbstractMarkerOptions<Any>): AbstractMarker {
        return HereMarker(marker, mapView)
    }

    override fun moveCamera(coords: AbstractCameraPosition, animate: Boolean) {
        TODO("Not yet implemented")
    }

    override fun moveCamera(position: AbstractPosition, animate: Boolean) {
        TODO("Not yet implemented")
    }

    override fun moveCamera(position: AbstractPosition, animate: Boolean, cancelableCallback: AnimationCallback) {
        TODO("Not yet implemented")
    }

    override fun moveCamera(lat: Double, lng: Double, animate: Boolean) {
        TODO("Not yet implemented")
    }

    override fun moveCamera(lat: Double, lng: Double, animate: Boolean, zoom: Float) {
        TODO("Not yet implemented")
    }

    override fun moveCamera(position: AbstractPosition, zoom: Float, animate: Boolean) {
        TODO("Not yet implemented")
    }

    override fun moveCamera(position: AbstractPosition, zoom: Float, cancelableCallback: AnimationCallback?) {
        TODO("Not yet implemented")
    }

    override fun moveCamera(zoom: Float, animate: Boolean) {
        TODO("Not yet implemented")
    }

    override fun moveCamera(bounds: AbstractBounds, padding: Int, animate: Boolean, cancelableCallback: AnimationCallback?) {
        TODO("Not yet implemented")
    }

    override fun moveCamera(bounds: AbstractBounds, width: Int, height: Int, padding: Int, animate: Boolean, cancelableCallback: AnimationCallback?) {
        TODO("Not yet implemented")
    }

    override fun moveCamera(to: LatLng, from: LatLng, padding: Int, animate: Boolean, cancelableCallback: AnimationCallback?) {
    }

    override fun moveCamera(points: ArrayList<Point>, padding: Int, animate: Boolean, cancelableCallback: AnimationCallback?) {
        TODO("Not yet implemented")
    }

    override fun moveCamera(builder: LatLngBounds.Builder, padding: Int, animate: Boolean, cancelableCallback: AnimationCallback?) {
        TODO("Not yet implemented")
    }

    override fun getCameraPosition(): AbstractCameraPosition {
        TODO("Not yet implemented")
    }

    override fun getPosition(): AbstractPosition {
        TODO("Not yet implemented")
    }

    override fun getZoom(): Float {
        TODO("Not yet implemented")
    }

    override fun addPolyline(polylineOptions: AbstractPolylineOptions): AbstractPolyline {
        return HerePolyLine(polylineOptions, mapView)
    }

    override fun setPadding(left: Int, top: Int, right: Int, bottom: Int) {
        TODO("Not yet implemented")
    }

    override fun stopAnimation() {
        TODO("Not yet implemented")
    }

    override fun setScrollGesturesEnabled(enabled: Boolean) {
        TODO("Not yet implemented")
    }

    override fun setOnCameraIdleListener(listener: CameraEventListener?) {
        TODO("Not yet implemented")
    }

    override fun setOnCameraMoveListener(listener: CameraEventListener?) {
        TODO("Not yet implemented")
    }

    override fun setOnCameraMoveStartedListener(listener: CameraStartMovingListener?) {
        if (cameraStartMovingListener != null) {
            cameraStartMovingListener = null
            cameraStartMovingListener = listener
        } else {
            cameraStartMovingListener = listener
        }
    }

    override fun setOnCameraMoveCanceledListener(listener: CameraEventListener?) {
        TODO("Not yet implemented")
    }

    override fun setOnMapClickListener(listener: GoogleMap.OnMapClickListener) {
        TODO("Not yet implemented")
    }

    override fun setMyLocationIndicator(enabled: Boolean,context: Context) {
        TODO("Not yet implemented")
    }

    override fun setZoomGesturesEnabled(enabled: Boolean) {
        TODO("Not yet implemented")
    }

    override fun isMyLocationIndicator(): Boolean {
        TODO("Not yet implemented")
    }

    override fun setMapTypeIsSat(isSta: Boolean) {
        TODO("Not yet implemented")
    }

    override fun setOnMarkerClickListener(listener: MarkerClickListener) {
        TODO("Not yet implemented")
    }

    override fun zoomBy(d: Float) {
        TODO("Not yet implemented")
    }

    override fun zoomBy(d: Float, finish: Runnable, onCancel: Runnable) {
        TODO("Not yet implemented")
    }

    override fun setMaxZoomLevel(d: Float) {
        TODO("Not yet implemented")
    }

    override fun setMinZoomLevel(d: Float) {
        TODO("Not yet implemented")
    }

    override fun animateCamera(position: AbstractPosition, zoom: Float, animate: Boolean) {
        mapView.camera.target = GeoCoordinates(position.lat, position.lng)
        mapView.camera.zoomLevel = zoom.toDouble()
    }

    override fun setOnMapLoadedCallback(var1: GoogleMap.OnMapLoadedCallback) {
        TODO("Not yet implemented")
    }

    override fun onResume() {
        TODO("Not yet implemented")
    }

    override fun onPause() {
        TODO("Not yet implemented")
    }

    override fun onDestroy() {
        TODO("Not yet implemented")
    }

    override fun onLowMemory() {
        TODO("Not yet implemented")
    }

    override fun projection(): Projection {
        TODO("Not yet implemented")
    }

    override fun darkMode(boolean: Boolean, context: Context) {
        TODO("Not yet implemented")
    }
}
