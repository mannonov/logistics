package com.myapp.logistics.map.polyline

import android.graphics.Color
import com.myapp.logistics.map.AbstractPosition

class AbstractPolylineOptions {
    val points: ArrayList<AbstractPosition> = ArrayList<AbstractPosition>()
    var color: Int = Color.BLACK
    var width: Float = 1F
    fun addAll(points: List<AbstractPosition>) {
        this.points.addAll(points)
    }
}
