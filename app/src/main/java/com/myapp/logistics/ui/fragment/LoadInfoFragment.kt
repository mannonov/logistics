package com.myapp.logistics.ui.fragment

import android.content.res.Configuration
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.navigation.fragment.navArgs
import by.kirich1409.viewbindingdelegate.viewBinding
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.here.sdk.routing.Route
import com.myapp.logistics.R
import com.myapp.logistics.databinding.FragmentLoadInfoBinding
import com.myapp.logistics.map.AbstractMap
import com.myapp.logistics.map.AbstractPosition
import com.myapp.logistics.map.GoogleMapsImpl
import com.myapp.logistics.map.camera.CameraStartMovingListener
import com.myapp.logistics.map.marker.AbstractMarkerOptions
import com.myapp.logistics.map.polyline.AbstractPolylineOptions
import com.myapp.logistics.model.Load
import com.myapp.logistics.util.Outcome
import com.myapp.logistics.util.addRepeatingJob
import com.myapp.logistics.viewmodel.LoadInfoViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoadInfoFragment : Fragment(R.layout.fragment_load_info) {

    private val binding: FragmentLoadInfoBinding by viewBinding(FragmentLoadInfoBinding::bind)
    private var abstractMap: AbstractMap? = null
    private val args: LoadInfoFragmentArgs by navArgs()
    private val viewModel by viewModels<LoadInfoViewModel>()
    private var aPoint: AbstractPosition? = null
    private var bPoint: AbstractPosition? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initMap(args.load)
        viewLifecycleOwner.addRepeatingJob(Lifecycle.State.STARTED) {
            viewModel.routeFlow.collect { outcome ->
                if (outcome is Outcome.Success) {
                    addPolyline(outcome.data)
                }
            }
        }
    }

    private fun initMap(load: Load) {
        val mapFragment =
            childFragmentManager.findFragmentById(R.id.google_map) as SupportMapFragment
        mapFragment.getMapAsync {
            val googleMapsImpl = GoogleMapsImpl(it)
            abstractMap = googleMapsImpl
            abstractMap?.setMyLocationIndicator(true, requireContext())
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
            aPoint = AbstractPosition(load.aPoint?.lat ?: 0.0, load.aPoint?.lng ?: 0.0)
            bPoint = AbstractPosition(load.bPoint?.lat ?: 0.0, load.bPoint?.lng ?: 0.0)
            googleMapsImpl.addMarker(
                AbstractMarkerOptions<Any>(aPoint!!).apply {
                    icon = R.drawable.point
                }
            )
            googleMapsImpl.addMarker(
                AbstractMarkerOptions<Any>(bPoint!!).apply {
                    icon = R.drawable.point
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
            viewModel.getRoute(aPoint!!, bPoint!!)
            abstractMap?.moveCamera(LatLng(aPoint?.lat ?: 0.0, aPoint?.lng ?: 0.0), LatLng(bPoint?.lat ?: 0.0, bPoint?.lng ?: 0.0), 200, true, null)
//            changeGotoMyLocationState(true)
        }
    }

    private fun addPolyline(route: Route) {
        Toast.makeText(
            requireContext(),
            "eta = ${route.duration.toHours()}:${route.duration.toMinutes()}:${route.duration.toSeconds()} \n" +
                "traffic delay h: ${route.trafficDelay.toHours()} \n" +
                "route handle : ${route.routeHandle?.handle} \n" +
                "length ${route.lengthInMeters} meters",
            Toast.LENGTH_SHORT
        ).show()
        val polyline = AbstractPolylineOptions()
        route.geometry.vertices.forEach {
            Log.d("dataRoad", "addPolyline: ${it.latitude} ${it.longitude}")
            polyline.points.add(AbstractPosition(it.latitude, it.longitude))
            polyline.width = 15f
        }
        abstractMap?.addPolyline(polyline)
    }
}
