package com.myapp.logistics.map

import com.here.sdk.core.GeoCoordinates
import com.here.sdk.core.GeoPolyline
import com.here.sdk.mapviewlite.MapPolyline
import com.here.sdk.mapviewlite.MapPolylineStyle
import com.here.sdk.mapviewlite.MapViewLite
import com.here.sdk.mapviewlite.PixelFormat
import com.here.sdk.routing.CarOptions
import com.here.sdk.routing.RoutingEngine
import com.here.sdk.routing.Waypoint
import com.myapp.logistics.map.camera.AbstractBounds
import com.myapp.logistics.map.polyline.AbstractPolyline
import com.myapp.logistics.map.polyline.AbstractPolylineOptions

class HerePolyLine constructor(private val polylineOptions: AbstractPolylineOptions, private val mapViewLite: MapViewLite) : AbstractPolyline() {

    init {
        val listGeo = polylineOptions.points.map { GeoCoordinates(it.lat, it.lng) }
        val wayPoints = listGeo.map { Waypoint(it) }
        val routingEngine = RoutingEngine()
        routingEngine.calculateRoute(wayPoints, CarOptions()) { routingError, routes ->
            if (routingError == null) {
                routes?.get(0)?.let {
                    mapViewLite.mapScene.addMapPolyline(
                        MapPolyline(
                            GeoPolyline(it.polyline).apply {
                            },
                            MapPolylineStyle().apply {
                                widthInPixels = 5.0
                                setColor(0xFF0000A0, PixelFormat.RGBA_8888)
                            }
                        ).apply {
                            isVisible = true
                        }
                    )
                }
            }
        }
    }

    override fun remove() {
        TODO("Not yet implemented")
    }

    override fun getBounds(): AbstractBounds {
        TODO("Not yet implemented")
    }
}
