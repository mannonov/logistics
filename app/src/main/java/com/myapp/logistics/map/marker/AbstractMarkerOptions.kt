package com.myapp.logistics.map.marker

import com.myapp.logistics.map.AbstractPosition

class AbstractMarkerOptions<I>(var latLng: AbstractPosition) {
    var anchorX: Float? = null
    var anchorY: Float? = null
    var rotation: Float? = null
    var icon: I? = null
    var zIndex: Int? = null
}
