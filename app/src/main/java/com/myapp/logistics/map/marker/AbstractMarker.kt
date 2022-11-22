package com.myapp.logistics.map.marker

import android.graphics.Point
import com.myapp.logistics.map.AbstractMap
import com.myapp.logistics.map.AbstractPosition

abstract class AbstractMarker {
    var marker: Any? = null
    var isSelected: Boolean = false
    abstract fun remove()
    abstract fun getRotation(): Float
    abstract fun getPosition(): AbstractPosition
    abstract fun setPosition(newPosition: AbstractPosition)

    abstract fun setRotation(newRotation: Float)

    abstract fun setIcon(icon: Any): Any

    abstract fun getScreenPosition(map: AbstractMap): Point?

    abstract fun showInfoWindow()

    abstract fun hideInfoWindow()

    abstract fun setVisible(visible: Boolean)
}
