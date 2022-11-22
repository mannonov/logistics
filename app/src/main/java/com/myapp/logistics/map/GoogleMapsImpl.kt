package com.myapp.logistics.map

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.Projection
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import com.google.android.gms.maps.model.MapStyleOptions
import com.myapp.logistics.R
import com.myapp.logistics.map.camera.*
import com.myapp.logistics.map.marker.AbstractMarker
import com.myapp.logistics.map.marker.AbstractMarkerOptions
import com.myapp.logistics.map.polyline.AbstractPolyline
import com.myapp.logistics.map.polyline.AbstractPolylineOptions

class GoogleMapsImpl(var m: GoogleMap) : AbstractMap() {

    private var onCameraIdleListener: CameraEventListener? = null
    private var onCameraMoveStartedListener: CameraStartMovingListener? = null
    private var onCameraMoveCanceledListener: CameraEventListener? = null
    private var onCameraMoveListener: CameraEventListener? = null
    private var onMapClickListener: GoogleMap.OnMapClickListener? = null
    private var onMarkerClickListener: MarkerClickListener? = null

    init {
        m.setOnCameraMoveStartedListener {
            onCameraMoveStartedListener?.onEvent(it)
        }
        m.setOnCameraIdleListener {
            onCameraIdleListener?.onEvent()
        }
        m.setOnCameraMoveCanceledListener {
            onCameraMoveCanceledListener?.onEvent()
        }
        m.setOnMapClickListener {
            onMapClickListener?.onMapClick(it)
        }
        m.setOnMarkerClickListener {
            onMarkerClickListener?.onClick(GoogleMarker(it)) ?: false
        }
    }

    override fun moveCamera(lat: Double, lng: Double, animate: Boolean, zoom: Float) {
        m.clear()

        val newPosition = CameraUpdateFactory.newLatLngZoom(LatLng(lat, lng), zoom)
        if (animate) {
            m.animateCamera(newPosition, animationSpeed, null)
        } else {
            m.moveCamera(newPosition)
        }
    }

    override fun zoomBy(d: Float, runnable: Runnable, onCancel: Runnable) {
        m.animateCamera(
            CameraUpdateFactory.zoomBy(d),
            300,
            object : GoogleMap.CancelableCallback {
                override fun onFinish() {
                    runnable.run()
                }

                override fun onCancel() {
                }
            }
        )
    }

    override fun zoomBy(d: Float) {
        m.animateCamera(CameraUpdateFactory.zoomBy(d), 500, null)
    }

    override fun setMaxZoomLevel(d: Float) {
        m.setMaxZoomPreference(d)
    }

    override fun setMinZoomLevel(d: Float) {
        m.setMinZoomPreference(d)
    }

    val animationSpeed = 700
    val slowAnimationSpeed = 700

    init {
        val mapUiSettings = m.uiSettings
        mapUiSettings.isCompassEnabled = false
        mapUiSettings.isMyLocationButtonEnabled = false
        mapUiSettings.isZoomControlsEnabled = false
        mapUiSettings.isMapToolbarEnabled = false
        mapUiSettings.isTiltGesturesEnabled = false
        mapUiSettings.isRotateGesturesEnabled = false
//        m.setMaxZoomPreference(Constants.MAX_ZOOM)
    }

    override fun moveCamera(
        position: AbstractPosition,
        zoom: Float,
        cancelableCallback: AnimationCallback?
    ) {
        m.animateCamera(
            CameraUpdateFactory.newLatLngZoom(LatLng(position.lat, position.lng), zoom),
            animationSpeed,
            object : GoogleMap.CancelableCallback {
                override fun onFinish() {
                    cancelableCallback?.onAnimationFinished()
                }

                override fun onCancel() {
                    cancelableCallback?.onAnimationCancelled()
                }
            }
        )
    }

    override fun addPolyline(polylineOptions: AbstractPolylineOptions): AbstractPolyline {
        return GooglePolyline(polylineOptions, m)
    }

    override fun setScrollGesturesEnabled(enabled: Boolean) {
        m.uiSettings.isScrollGesturesEnabled = enabled
    }

    @SuppressLint("MissingPermission")
    override fun setMyLocationIndicator(enabled: Boolean, context: Context) {
        if (ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED &&
            ActivityCompat.checkSelfPermission(
                    context,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ) == PackageManager.PERMISSION_GRANTED
        ) {
            m.isMyLocationEnabled = enabled
        }
    }

    override fun setZoomGesturesEnabled(enabled: Boolean) {
        m.uiSettings.isZoomGesturesEnabled = enabled
    }

    override fun isMyLocationIndicator(): Boolean {
        return m.isMyLocationEnabled
    }

    override fun setPadding(left: Int, top: Int, right: Int, bottom: Int) {
        m.setPadding(left, top, right, bottom)
    }

    override fun addMarker(marker: AbstractMarkerOptions<Any>): AbstractMarker {
        return GoogleMarker(marker, m)
    }

    override fun getCameraPosition(): AbstractCameraPosition {
        return AbstractCameraPosition(
            AbstractPosition(m.cameraPosition.target.latitude, m.cameraPosition.target.longitude),
            m.cameraPosition.zoom
        )
    }

    override fun getPosition(): AbstractPosition {
        return AbstractPosition(m.cameraPosition.target.latitude, m.cameraPosition.target.longitude)
    }

    override fun getZoom(): Float {
        return m.cameraPosition.zoom
    }

    override fun stopAnimation() {
        m.stopAnimation()
    }

    override fun animateCamera(position: AbstractPosition, zoom: Float, animate: Boolean) {
        val newPosition =
            CameraUpdateFactory.newLatLngZoom(LatLng(position.lat, position.lng), zoom)
        if (animate) {
            m.animateCamera(newPosition, slowAnimationSpeed, null)
        } else {
            m.moveCamera(newPosition)
        }
    }

    override fun setOnMapLoadedCallback(var1: GoogleMap.OnMapLoadedCallback) {
        m.setOnMapLoadedCallback(var1)
    }

    override fun onResume() {
    }

    override fun onPause() {
    }

    override fun onDestroy() {
        m.clear()
        onCameraIdleListener = null
        onCameraMoveStartedListener = null
        onCameraMoveCanceledListener = null
        onCameraMoveListener = null
        onMapClickListener = null
        onMarkerClickListener = null
    }

    override fun onLowMemory() {
    }

    override fun projection(): Projection {
        return m.projection
    }

    override fun darkMode(boolean: Boolean, context: Context) {
        if (boolean) {
            m.setMapStyle(MapStyleOptions.loadRawResourceStyle(context, R.raw.dark_style_json))
        } else {
            m.setMapStyle(MapStyleOptions.loadRawResourceStyle(context, R.raw.light_style_json))
        }
    }

    override fun moveCamera(coords: AbstractCameraPosition, animate: Boolean) {
        val newPosition = CameraUpdateFactory.newLatLngZoom(
            LatLng(coords.position.lat, coords.position.lng),
            coords.zoom
        )
        if (animate) {
            m.animateCamera(newPosition, animationSpeed, null)
        } else {
            m.moveCamera(newPosition)
        }
    }

    override fun moveCamera(position: AbstractPosition, animate: Boolean) {
        val newPosition = CameraUpdateFactory.newLatLngZoom(
            LatLng(position.lat, position.lng),
            m.cameraPosition.zoom
        )
        if (animate) {
            m.animateCamera(newPosition, animationSpeed, null)
        } else {
            m.moveCamera(newPosition)
        }
    }

    override fun moveCamera(zoom: Float, animate: Boolean) {
        val newPosition = CameraUpdateFactory.zoomTo(zoom)
        if (animate) {
            m.animateCamera(newPosition, animationSpeed, null)
        } else {
            m.moveCamera(newPosition)
        }
    }

    @RequiresApi(Build.VERSION_CODES.N)
    override fun moveCamera(
        bounds: AbstractBounds,
        padding: Int,
        animate: Boolean,
        cancelableCallback: AnimationCallback?
    ) {
        try {
            val builder = LatLngBounds.Builder()
            bounds.points.forEach {
                builder.include(LatLng(it.lat, it.lng))
            }

            val cameraUpdate = CameraUpdateFactory.newLatLngBounds(builder.build(), padding)

            if (animate) {
                m.animateCamera(
                    cameraUpdate,
                    2000,
                    object : GoogleMap.CancelableCallback {
                        override fun onCancel() {
                            if (cancelableCallback != null) {
                                cancelableCallback.onAnimationCancelled()
                            }
                        }

                        override fun onFinish() {
                            if (cancelableCallback != null) {
                                cancelableCallback.onAnimationFinished()
                            }
                        }
                    }
                )
            } else {
                m.moveCamera(cameraUpdate)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    @RequiresApi(Build.VERSION_CODES.N)
    override fun moveCamera(
        bounds: AbstractBounds,
        width: Int,
        height: Int,
        padding: Int,
        animate: Boolean,
        cancelableCallback: AnimationCallback?
    ) {
        try {
            val builder = LatLngBounds.Builder()
            bounds.points.forEach {
                builder.include(LatLng(it.lat, it.lng))
            }
            val cameraUpdate =
                CameraUpdateFactory.newLatLngBounds(builder.build(), width, height, padding)
            if (animate) {
                android.os.Handler().post {
                    m.animateCamera(
                        cameraUpdate,
                        2000,
                        object : GoogleMap.CancelableCallback {
                            override fun onCancel() {
                                if (cancelableCallback != null) {
                                    cancelableCallback.onAnimationCancelled()
                                }
                            }

                            override fun onFinish() {
                                if (cancelableCallback != null) {
                                    cancelableCallback.onAnimationFinished()
                                }
                            }
                        }
                    )
                }
            } else {
                android.os.Handler().post {
                    m.moveCamera(cameraUpdate)
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun moveCamera(
        builder: LatLngBounds.Builder,
        padding: Int,
        animate: Boolean,
        cancelableCallback: AnimationCallback?
    ) {
        try {
            val cameraUpdate = CameraUpdateFactory.newLatLngBounds(builder.build(), padding)
            if (animate) {
                m.animateCamera(
                    cameraUpdate,
                    2000,
                    object : GoogleMap.CancelableCallback {
                        override fun onCancel() {
                            if (cancelableCallback != null) {
                                cancelableCallback.onAnimationCancelled()
                            }
                        }

                        override fun onFinish() {
                            if (cancelableCallback != null) {
                                cancelableCallback.onAnimationFinished()
                            }
                        }
                    }
                )
            } else {
                m.moveCamera(cameraUpdate)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun moveCamera(
        to: LatLng,
        from: LatLng,
        padding: Int,
        animate: Boolean,
        cancelableCallback: AnimationCallback?
    ) {
        val builder = LatLngBounds.Builder()
        builder.include(to)
        builder.include(from)

        try {
            val cameraUpdate = CameraUpdateFactory.newLatLngBounds(builder.build(), padding)
            if (animate) {
                m.animateCamera(
                    cameraUpdate,
                    2000,
                    object : GoogleMap.CancelableCallback {
                        override fun onCancel() {
                            if (cancelableCallback != null) {
                                cancelableCallback.onAnimationCancelled()
                            }
                        }

                        override fun onFinish() {
                            if (cancelableCallback != null) {
                                cancelableCallback.onAnimationFinished()
                            }
                        }
                    }
                )
            } else {
                m.moveCamera(cameraUpdate)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun moveCamera(
        points: ArrayList<Point>,
        padding: Int,
        animate: Boolean,
        cancelableCallback: AnimationCallback?
    ) {
        val builder = LatLngBounds.Builder()

        points.forEach {
            builder.include(LatLng(it.position.lat, it.position.lng))
        }

        try {
            val cameraUpdate = CameraUpdateFactory.newLatLngBounds(builder.build(), padding)
            if (animate) {
                m.animateCamera(
                    cameraUpdate,
                    2000,
                    object : GoogleMap.CancelableCallback {
                        override fun onCancel() {
                            if (cancelableCallback != null) {
                                cancelableCallback.onAnimationCancelled()
                            }
                        }

                        override fun onFinish() {
                            if (cancelableCallback != null) {
                                cancelableCallback.onAnimationFinished()
                            }
                        }
                    }
                )
            } else {
                m.moveCamera(cameraUpdate)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun moveCamera(lat: Double, lng: Double, animate: Boolean) {
        val newPosition = CameraUpdateFactory.newLatLngZoom(LatLng(lat, lng), m.cameraPosition.zoom)
        if (animate) {
            m.animateCamera(newPosition, animationSpeed, null)
        } else {
            m.moveCamera(newPosition)
        }
    }

    override fun moveCamera(
        position: AbstractPosition,
        animate: Boolean,
        cancelableCallback: AnimationCallback
    ) {
        if (animate) {
            m.animateCamera(
                CameraUpdateFactory.newLatLngZoom(
                    LatLng(position.lat, position.lng),
                    m.cameraPosition.zoom
                ),
                animationSpeed,
                object : GoogleMap.CancelableCallback {
                    override fun onFinish() {
                        cancelableCallback.onAnimationFinished()
                    }

                    override fun onCancel() {
                        cancelableCallback.onAnimationCancelled()
                    }
                }
            )
        } else {
            m.moveCamera(
                CameraUpdateFactory.newLatLngZoom(
                    LatLng(position.lat, position.lng),
                    m.cameraPosition.zoom
                )
            )
        }
    }

    override fun moveCamera(position: AbstractPosition, zoom: Float, animate: Boolean) {
        if (animate) {
            m.animateCamera(
                CameraUpdateFactory.newLatLngZoom(
                    LatLng(position.lat, position.lng),
                    zoom
                ),
                animationSpeed,
                null
            )
        } else {
            m.moveCamera(
                CameraUpdateFactory.newLatLngZoom(
                    LatLng(position.lat, position.lng),
                    zoom
                )
            )
        }
    }

    override fun setOnCameraIdleListener(listener: CameraEventListener?) {
        if (onCameraIdleListener != null) {
            onCameraIdleListener = null
            onCameraIdleListener = listener
        } else {
            onCameraIdleListener = listener
        }
    }

    override fun setOnCameraMoveStartedListener(listener: CameraStartMovingListener?) {
        if (onCameraMoveStartedListener != null) {
            onCameraMoveStartedListener = null
            onCameraMoveStartedListener = listener
        } else {
            onCameraMoveStartedListener = listener
        }
    }

    override fun setOnCameraMoveListener(listener: CameraEventListener?) {
        if (onCameraMoveListener != null) {
            onCameraMoveListener = null
            onCameraMoveListener = listener
        } else {
            onCameraMoveListener = listener
        }
    }

    override fun setOnCameraMoveCanceledListener(listener: CameraEventListener?) {
        if (onCameraMoveCanceledListener != null) {
            onCameraMoveCanceledListener = null
            onCameraMoveCanceledListener = listener
        } else {
            onCameraMoveCanceledListener = listener
        }
    }

    override fun setOnMarkerClickListener(listener: MarkerClickListener) {
        if (onMarkerClickListener != null) {
            onMarkerClickListener = null
            onMarkerClickListener = listener
        } else {
            onMarkerClickListener = listener
        }
    }

    override fun setOnMapClickListener(listener: GoogleMap.OnMapClickListener) {
        if (onMapClickListener != null) {
            onMapClickListener = null
            onMapClickListener = listener
        } else {
            onMapClickListener = listener
        }
    }

    override fun setMapTypeIsSat(isSat: Boolean) {
        if (isSat) {
            m.mapType = GoogleMap.MAP_TYPE_HYBRID
        } else {
            m.mapType = GoogleMap.MAP_TYPE_NORMAL
        }
    }
}
