package com.myapp.logistics.map.polyline

import com.myapp.logistics.map.camera.AbstractBounds

abstract class AbstractPolyline {
    abstract fun remove()
    abstract fun getBounds(): AbstractBounds
}
