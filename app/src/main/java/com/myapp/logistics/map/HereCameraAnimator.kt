package com.myapp.logistics.map

import android.animation.*
import com.here.sdk.core.GeoCoordinates
import com.here.sdk.mapviewlite.Camera
import com.here.sdk.mapviewlite.CameraUpdate

const val DEFAULT_ZOOM_LEVEL = 5.0

class HereCameraAnimator constructor(private val camera: Camera) {

    private val valueAnimatorList: ArrayList<ValueAnimator> = ArrayList()
    private var timeInterpolator: TimeInterpolator? = null
    private var animatorSet: AnimatorSet? = null
    private lateinit var onAnimationEndListener: AnimationEndListener

    fun moveCamera(position: AbstractPosition, zoomLevel: Double = DEFAULT_ZOOM_LEVEL) {
        val targetCameraUpdate: CameraUpdate = createTargetCameraUpdate(position, zoomLevel)
        createAnimation(targetCameraUpdate)
        startAnimation(targetCameraUpdate)
    }

    private fun createTargetCameraUpdate(position: AbstractPosition, zoomLevel: Double = DEFAULT_ZOOM_LEVEL): CameraUpdate {
        val targetTilt = 0.0

        // Take the shorter bearing difference.
        val targetBearing: Double = if (camera.bearing > 180) 360.0 else 0.toDouble()
        return CameraUpdate(targetTilt, targetBearing, zoomLevel, GeoCoordinates(position.lat, position.lng))
    }

    private fun createAnimation(cameraUpdate: CameraUpdate) {
        valueAnimatorList.clear()

        // Interpolate current values for zoom, tilt, bearing, lat/lon to the desired new values.
        val zoomValueAnimator: ValueAnimator = createAnimator(camera.zoomLevel, cameraUpdate.zoomLevel)
        val tiltValueAnimator: ValueAnimator = createAnimator(camera.tilt, cameraUpdate.tilt)
        val bearingValueAnimator: ValueAnimator = createAnimator(camera.bearing, cameraUpdate.bearing)
        val latitudeValueAnimator: ValueAnimator = createAnimator(
            camera.target.latitude,
            cameraUpdate.target.latitude
        )
        val longitudeValueAnimator: ValueAnimator = createAnimator(
            camera.target.longitude,
            cameraUpdate.target.longitude
        )
        valueAnimatorList.add(zoomValueAnimator)
        valueAnimatorList.add(tiltValueAnimator)
        valueAnimatorList.add(bearingValueAnimator)
        valueAnimatorList.add(latitudeValueAnimator)
        valueAnimatorList.add(longitudeValueAnimator)

        // Update all values together.
        longitudeValueAnimator.addUpdateListener {
            val zoom = zoomValueAnimator.animatedValue as Float
            val tilt = tiltValueAnimator.animatedValue as Float
            val bearing = bearingValueAnimator.animatedValue as Float
            val latitude = latitudeValueAnimator.animatedValue as Float
            val longitude = longitudeValueAnimator.animatedValue as Float
            val intermediateGeoCoordinates = GeoCoordinates(latitude.toDouble(), longitude.toDouble())
            camera.updateCamera(CameraUpdate(tilt.toDouble(), bearing.toDouble(), zoom.toDouble(), intermediateGeoCoordinates))
        }
    }

    private fun createAnimator(from: Double, to: Double): ValueAnimator {
        val valueAnimator = ValueAnimator.ofFloat(from.toFloat(), to.toFloat())
        if (timeInterpolator != null) {
            valueAnimator.interpolator = timeInterpolator
        }
        return valueAnimator
    }

    private fun startAnimation(cameraUpdate: CameraUpdate) {
        if (animatorSet != null) {
            animatorSet?.cancel()
        }
        animatorSet = AnimatorSet()
        animatorSet?.playTogether(valueAnimatorList as Collection<Animator>?)
        animatorSet?.duration = 2000
        animatorSet?.start()
        animatorSet?.addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator) {
                camera.updateCamera(cameraUpdate)
                if (this@HereCameraAnimator::onAnimationEndListener.isInitialized) {
                    onAnimationEndListener.onEnd()
                }
            }
        })
    }

    fun setAnimationEndListener(onAnimationEndListener: AnimationEndListener) {
        this.onAnimationEndListener = onAnimationEndListener
    }

    class AnimationEndListener(val onEnd: () -> Unit)
}
