package com.myapp.logistics.map.camera

import com.myapp.logistics.map.GoogleMarker

interface CameraEventListener {
    fun onEvent()
}

interface MarkerClickListener {
    fun onClick(m: GoogleMarker): Boolean
}
