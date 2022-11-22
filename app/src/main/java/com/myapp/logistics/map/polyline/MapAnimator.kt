package com.myapp.logistics.map.polyline

import android.animation.*
import android.graphics.Color
import android.view.animation.AccelerateDecelerateInterpolator
import android.view.animation.AccelerateInterpolator
import android.view.animation.DecelerateInterpolator
import androidx.annotation.Keep
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Polyline
import com.google.android.gms.maps.model.PolylineOptions

/**
 * Created by amal.chandran on 22/12/16.
 */
class MapAnimator private constructor() {
    private var backgroundPolyline: Polyline? = null
    private var foregroundPolyline: Polyline? = null
    private var optionsForeground: PolylineOptions? = null
    private var firstRunAnimSet: AnimatorSet? = null
    private var secondLoopRunAnimSet: AnimatorSet? = null
    fun animateRoute(googleMap: GoogleMap, bangaloreRoute: List<LatLng?>, width: Int) {
        firstRunAnimSet = if (firstRunAnimSet == null) {
            AnimatorSet()
        } else {
            firstRunAnimSet?.removeAllListeners()
            firstRunAnimSet?.end()
            firstRunAnimSet?.cancel()
            AnimatorSet()
        }
        secondLoopRunAnimSet = if (secondLoopRunAnimSet == null) {
            AnimatorSet()
        } else {
            secondLoopRunAnimSet?.removeAllListeners()
            secondLoopRunAnimSet?.end()
            secondLoopRunAnimSet?.cancel()
            AnimatorSet()
        }
        // Reset the polylines
        if (foregroundPolyline != null) foregroundPolyline?.remove()
        if (backgroundPolyline != null) backgroundPolyline?.remove()
        val optionsBackground = PolylineOptions().add(bangaloreRoute[0]).color(COLOR_PRIMARY_DARK)
            .width(width.toFloat())
        backgroundPolyline = googleMap.addPolyline(optionsBackground)
        optionsForeground =
            PolylineOptions().add(bangaloreRoute[0]).color(COLOR_PRIMARY).width(width.toFloat())
        foregroundPolyline = googleMap.addPolyline(optionsForeground!!)
        val percentageCompletion = ValueAnimator.ofInt(0, 100)
        percentageCompletion.duration = 2000
        percentageCompletion.interpolator = DecelerateInterpolator()
        percentageCompletion.addUpdateListener { animation ->
            val foregroundPoints = backgroundPolyline!!.points
            val percentageValue = animation.animatedValue as Int
            val pointcount = foregroundPoints.size
            val countTobeRemoved = (pointcount * (percentageValue / 100.0f)).toInt()
            val subListTobeRemoved = foregroundPoints.subList(0, countTobeRemoved)
            subListTobeRemoved.clear()
            foregroundPolyline!!.points = foregroundPoints
        }
        percentageCompletion.addListener(object : Animator.AnimatorListener {
            override fun onAnimationStart(animation: Animator) {}
            override fun onAnimationEnd(animation: Animator) {
                foregroundPolyline?.color = COLOR_PRIMARY_DARK
                foregroundPolyline?.points = backgroundPolyline?.points!!
            }

            override fun onAnimationCancel(animation: Animator) {}
            override fun onAnimationRepeat(animation: Animator) {}
        })
        val colorAnimation =
            ValueAnimator.ofObject(ArgbEvaluator(), COLOR_PRIMARY_DARK, COLOR_PRIMARY)
        colorAnimation.interpolator = AccelerateInterpolator()
        colorAnimation.duration = 1200 // milliseconds
        colorAnimation.addUpdateListener { animator ->
            foregroundPolyline!!.color = animator.animatedValue as Int
        }
        val foregroundRouteAnimator = ObjectAnimator.ofObject(
            this,
            "routeIncreaseForward",
            RouteEvaluator(),
            *bangaloreRoute.toTypedArray()
        )
        foregroundRouteAnimator.interpolator = AccelerateDecelerateInterpolator()
        foregroundRouteAnimator.addListener(object : Animator.AnimatorListener {
            override fun onAnimationStart(animation: Animator) {}
            override fun onAnimationEnd(animation: Animator) {
                backgroundPolyline?.points = foregroundPolyline?.points!!
            }

            override fun onAnimationCancel(animation: Animator) {}
            override fun onAnimationRepeat(animation: Animator) {}
        })
        foregroundRouteAnimator.duration = 1600
        //        foregroundRouteAnimator.start();
        firstRunAnimSet?.playSequentially(
            foregroundRouteAnimator,
            percentageCompletion
        )
        firstRunAnimSet!!.addListener(object : Animator.AnimatorListener {
            override fun onAnimationStart(animation: Animator) {}
            override fun onAnimationEnd(animation: Animator) {
                secondLoopRunAnimSet?.start()
            }

            override fun onAnimationCancel(animation: Animator) {}
            override fun onAnimationRepeat(animation: Animator) {}
        })
        secondLoopRunAnimSet?.playSequentially(
            colorAnimation,
            percentageCompletion
        )
        secondLoopRunAnimSet?.startDelay = 200
        secondLoopRunAnimSet?.addListener(object : Animator.AnimatorListener {
            override fun onAnimationStart(animation: Animator) {}
            override fun onAnimationEnd(animation: Animator) {
                secondLoopRunAnimSet?.start()
            }

            override fun onAnimationCancel(animation: Animator) {}
            override fun onAnimationRepeat(animation: Animator) {}
        })
        firstRunAnimSet?.start()
    }

    // This is an handy method to call if you want to remove the polyline because of some condition like back press
    fun stopAndRemovePolyLine() {
        if (mapAnimator != null) {
            backgroundPolyline?.remove()
            foregroundPolyline?.remove()
            firstRunAnimSet?.cancel()
            secondLoopRunAnimSet?.cancel()
        }
    }

    /**
     * This will be invoked by the ObjectAnimator multiple times. Mostly every 16ms.
     */
    @Keep
    fun setRouteIncreaseForward(endLatLng: LatLng) {
        val foregroundPoints = foregroundPolyline!!.points
        foregroundPoints.add(endLatLng)
        foregroundPolyline?.points = foregroundPoints
    }

    companion object {
        private var mapAnimator: MapAnimator? = null
        val COLOR_PRIMARY = Color.parseColor("#3383EC")
        val COLOR_PRIMARY_DARK = Color.parseColor("#3383EC")
        val instance: MapAnimator?
            get() {
                if (mapAnimator == null) mapAnimator = MapAnimator()
                return mapAnimator
            }
    }
}
