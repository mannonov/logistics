package com.myapp.logistics.map

import android.animation.Animator
import android.animation.ObjectAnimator
import android.graphics.Bitmap
import android.graphics.Point
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.*
import com.myapp.logistics.map.marker.AbstractMarker
import com.myapp.logistics.map.marker.AbstractMarkerOptions

class GoogleMarker() : AbstractMarker() {

    constructor(aMarker: AbstractMarkerOptions<Any>, maps: GoogleMap) : this() {
        val icon: BitmapDescriptor

        val markerIcon = aMarker.icon

        icon = applyIcon(markerIcon!!)

        val markerOptions = MarkerOptions()
            .icon(icon)
            .position(LatLng(aMarker.latLng.lat, aMarker.latLng.lng))
            .rotation(aMarker.latLng.rotation)
        markerOptions.apply {
            if (aMarker.anchorX != null && aMarker.anchorY != null) {
                this.anchor(aMarker.anchorX!!, aMarker.anchorY!!)
            }
        }

        this.marker = maps.addMarker(markerOptions)
    }

    constructor(marker: Marker) : this() {
        this.marker = marker
    }

    override fun setPosition(newPosition: AbstractPosition) {
        (marker as Marker).position = LatLng(newPosition.lat, newPosition.lng)
    }

    override fun setRotation(newRotation: Float) {
        (marker as Marker).rotation = newRotation
    }

    fun applyIcon(markerIcon: Any): BitmapDescriptor {
        val icon: BitmapDescriptor = when (markerIcon) {
            is Int -> {
                BitmapDescriptorFactory.fromResource(markerIcon)
            }
            is Bitmap -> {
                BitmapDescriptorFactory.fromBitmap(markerIcon)
            }
            else -> {
                BitmapDescriptorFactory.defaultMarker()
            }
        }
        return icon
    }

    override fun setVisible(visible: Boolean) {
        (marker as Marker).isVisible = visible
    }

    override fun setIcon(markerIcon: Any) {
        val icon: BitmapDescriptor = when (markerIcon) {
            is Int -> {
                BitmapDescriptorFactory.fromResource(markerIcon)
            }
            is Bitmap -> {
                BitmapDescriptorFactory.fromBitmap(markerIcon)
            }
            else -> {
                BitmapDescriptorFactory.defaultMarker()
            }
        }

        (marker as Marker).setIcon(icon)
    }

    override fun showInfoWindow() {
        (marker as Marker).showInfoWindow()
    }

    override fun hideInfoWindow() {
        (marker as Marker).hideInfoWindow()
    }

    override fun getRotation(): Float {
        return if (marker != null) (marker as Marker).rotation else 0f
    }

    override fun getPosition(): AbstractPosition {
        return if (marker != null) AbstractPosition(
            (marker as Marker).position.latitude,
            (marker as Marker).position.longitude
        ) else AbstractPosition(0.0, 0.0) // TODO OPTIMZE IT
    }

    override fun getScreenPosition(map: AbstractMap): Point? {
        return if (marker != null) {
            val pos = (marker as Marker).position
            return map.projection().toScreenLocation(pos)
        } else {
            return null
        }
    }

    override fun remove() {
        val animator = ObjectAnimator.ofFloat((marker as Marker), "alpha", 1f, 0f)
        animator.addListener(object : Animator.AnimatorListener {
            override fun onAnimationEnd(animator: Animator) {
                (marker as Marker).remove()
            }

            override fun onAnimationStart(animator: Animator) {}
            override fun onAnimationCancel(animator: Animator) {
                (marker as Marker).remove()
            }

            override fun onAnimationRepeat(animator: Animator) {}
        })
        animator.setDuration(150).start()
    }
}
