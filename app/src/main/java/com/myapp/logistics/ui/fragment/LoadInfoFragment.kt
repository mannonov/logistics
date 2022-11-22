package com.myapp.logistics.ui.fragment

import android.content.res.Configuration
import android.graphics.BitmapFactory
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import by.kirich1409.viewbindingdelegate.viewBinding
import com.google.android.gms.maps.SupportMapFragment
import com.myapp.logistics.R
import com.myapp.logistics.databinding.FragmentLoadInfoBinding
import com.myapp.logistics.map.AbstractMap
import com.myapp.logistics.map.AbstractPosition
import com.myapp.logistics.map.GoogleMapsImpl
import com.myapp.logistics.map.camera.CameraStartMovingListener
import com.myapp.logistics.map.marker.AbstractMarkerOptions
import com.myapp.logistics.model.Load

class LoadInfoFragment : Fragment(R.layout.fragment_load_info) {

    private val binding: FragmentLoadInfoBinding by viewBinding(FragmentLoadInfoBinding::bind)
    private var abstractMap: AbstractMap? = null
    private val args: LoadInfoFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initMap(args.load)
    }

    private fun initMap(load: Load) {
        val mapFragment =
            childFragmentManager.findFragmentById(R.id.google_map) as SupportMapFragment
        mapFragment.getMapAsync {
            val googleMapsImpl = GoogleMapsImpl(it)
            if (abstractMap != null) {
                abstractMap = null
                abstractMap = googleMapsImpl
            } else {
                abstractMap = googleMapsImpl
            }
            googleMapsImpl.setMyLocationIndicator(true, requireContext())
//            viewModel.getLastKnownLocation()
            when (context?.resources?.configuration?.uiMode?.and(Configuration.UI_MODE_NIGHT_MASK)) {
                Configuration.UI_MODE_NIGHT_YES -> {
                    abstractMap?.darkMode(true, requireContext())
                }
                Configuration.UI_MODE_NIGHT_NO -> {
                    abstractMap?.darkMode(false, requireContext())
                }
                Configuration.UI_MODE_NIGHT_UNDEFINED -> {
                    abstractMap?.darkMode(false, requireContext())
                }
            }
            abstractMap?.addMarker(
                AbstractMarkerOptions<Any>(AbstractPosition(load.aPoint?.lat ?: 0.0, load.aPoint?.lng ?: 0.0)).apply {
                    icon = BitmapFactory.decodeResource(resources, R.drawable.ic_a_point)
                }
            )
            abstractMap?.setOnCameraMoveStartedListener(object : CameraStartMovingListener {
                override fun onEvent(reason: Int) {
//                    changeGotoMyLocationState(false)
                }
            })
            load.aPoint?.let { position ->
                abstractMap?.moveCamera(position.lat ?: 0.0, position.lng ?: 0.0, false, 17F)
            }
        }
    }
}
