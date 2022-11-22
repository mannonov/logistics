package com.myapp.logistics.map // ktlint-disable filename

import android.location.Location
import java.io.Serializable
import kotlin.math.atan2
import kotlin.math.cos
import kotlin.math.sin
import kotlin.math.sqrt

/**
 * The class {@code [AbstractPosition]} contains methods for
 *  calculate distance between two points
 */
class AbstractPosition(var lat: Double, var lng: Double, var rotation: Float) :
    Serializable {
    constructor(lat: Double, lng: Double) : this(lat, lng, 0f)

    /**
     * Calculates distance between two points
     * @param lat1 Latitude of first point
     * @param lng1 Longitude of first point
     * @param lat2 Latitude of second point
     * @param lng2 Longitude of second point
     * @return distance between two points
     */
    private fun distFrom(lat1: Double, lng1: Double, lat2: Double, lng2: Double): Float {
        val earthRadius = 6371000.0 // meters
        val dLat = Math.toRadians((lat2 - lat1))
        val dLng = Math.toRadians((lng2 - lng1))
        val a = sin(dLat / 2) * sin(dLat / 2)
        +cos(Math.toRadians(lat1)) * cos(Math.toRadians(lat2)) *
            sin(dLng / 2) * sin(dLng / 2)
        val c = 2 * atan2(sqrt(a), sqrt(1 - a))

        return (earthRadius * c).toFloat()
    }

    fun calculateDistanceWith(abstractPosition: AbstractPosition): Float {
        return calculateDistanceWith(this, abstractPosition)
    }

    fun deltaLatLng(abstractPosition: AbstractPosition): Float {
        val distance = FloatArray(2)
        try {
            Location.distanceBetween(abstractPosition.lat, abstractPosition.lng, lat, lng, distance)
            return distance[0]
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return Float.MAX_VALUE
    }

    companion object {
        /**
         * Calculates distance between two [AbstractPosition]
         * @param position the position to compare
         * @return distance between two AbstractPosition
         */
        fun calculateDistanceWith(
            abstractPosition: AbstractPosition,
            position: AbstractPosition
        ): Float {
            return abstractPosition.distFrom(
                position.lat,
                position.lng,
                abstractPosition.lat,
                abstractPosition.lng
            )
        }
    }
}
