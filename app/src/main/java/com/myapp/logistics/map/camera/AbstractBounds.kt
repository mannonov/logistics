package com.myapp.logistics.map.camera

import com.myapp.logistics.map.AbstractPosition

class AbstractBounds {
    val points: ArrayList<AbstractPosition> = ArrayList()
    fun include(position: AbstractPosition) {
        points.add(position)
    }
}
