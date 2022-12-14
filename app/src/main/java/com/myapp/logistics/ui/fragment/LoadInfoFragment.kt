package com.myapp.logistics.ui.fragment

import android.content.res.Configuration
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import by.kirich1409.viewbindingdelegate.viewBinding
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.here.sdk.routing.Route
import com.myapp.logistics.R
import com.myapp.logistics.databinding.FragmentLoadInfoBinding
import com.myapp.logistics.dialog.LogisticDialog
import com.myapp.logistics.map.AbstractMap
import com.myapp.logistics.map.AbstractPosition
import com.myapp.logistics.map.GoogleMapsImpl
import com.myapp.logistics.map.camera.CameraStartMovingListener
import com.myapp.logistics.map.marker.AbstractMarkerOptions
import com.myapp.logistics.map.polyline.AbstractPolylineOptions
import com.myapp.logistics.model.Driver
import com.myapp.logistics.model.Load
import com.myapp.logistics.util.*
import com.myapp.logistics.viewmodel.LoadInfoViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class LoadInfoFragment : Fragment(R.layout.fragment_load_info) {

    @Inject
    lateinit var prefs: LogisticsPref

    private val binding: FragmentLoadInfoBinding by viewBinding(FragmentLoadInfoBinding::bind)
    private var abstractMap: AbstractMap? = null
    private val args: LoadInfoFragmentArgs by navArgs()
    private val viewModel by viewModels<LoadInfoViewModel>()
    private var aPoint: AbstractPosition? = null
    private var bPoint: AbstractPosition? = null
    private lateinit var bottomSheetBehavior: BottomSheetBehavior<*>

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initMap(args.load)
        setData(args.load)
        bottomSheetBehavior = BottomSheetBehavior.from(binding.bottomSheet)
        bottomSheetBehavior.apply {
            isHideable = false
            peekHeight = 900
            isDraggable = true
        }
        viewLifecycleOwner.addRepeatingJob(Lifecycle.State.STARTED) {
            viewModel.routeFlow.collect { outcome ->
                if (outcome is Outcome.Success) {
                    addPolyline(outcome.data)
                    abstractMap?.addMarker(
                        AbstractMarkerOptions<Any>(aPoint!!).apply {
                            icon = R.drawable.ic_point_start
                        }
                    )
                    abstractMap?.addMarker(
                        AbstractMarkerOptions<Any>(bPoint!!).apply {
                            icon = R.drawable.ic_point_end
                        }
                    )
                }
            }
        }
        viewLifecycleOwner.addRepeatingJob(Lifecycle.State.STARTED) {
            viewModel.acceptLoadFlow.collect { outcome ->
                if (outcome is Outcome.Success) {
                    setFragmentResult(this@LoadInfoFragment.javaClass.simpleName, bundleOf(Constants.ORDER_STATUS to Constants.ACTIVE))
                    findNavController().popBackStack()
                }
            }
        }
        viewLifecycleOwner.addRepeatingJob(Lifecycle.State.STARTED) {
            viewModel.driverFlow.collect { outcome ->
                if (outcome is Outcome.Success) {
                    setDriverData(driver = outcome.data)
                }
            }
        }
        viewLifecycleOwner.addRepeatingJob(Lifecycle.State.STARTED) {
            viewModel.finishLoadFlow.collect { outcome ->
                if (outcome is Outcome.Success) {
                    setFragmentResult(this@LoadInfoFragment.javaClass.simpleName, bundleOf(Constants.ORDER_STATUS to Constants.COMPLETED))
                    findNavController().popBackStack()
                }
            }
        }
        binding.btnAcceptOrder.onClick {
            with(LogisticDialog(requireContext(), "Are you sure accept Order?")) {
                setYesClickListener(
                    yesClickListener = LogisticDialog.YesClickListener {
                        viewModel.acceptLoad(args.load, prefs.driver.id.toString())
                    }
                )
                show()
            }
        }
        binding.btnFinishOrder.onClick {
            with(LogisticDialog(requireContext(), "Are you sure finish Order?")) {
                setYesClickListener(
                    yesClickListener = LogisticDialog.YesClickListener {
                        viewModel.finishLoad(args.load)
                    }
                )
                show()
            }
        }
    }

    private fun setData(load: Load) {
        if (load.attachedDriverId != null) {
            viewModel.getDriverData(load.attachedDriverId.toString())
        }
        with(binding) {
            tvPointA.text = load.aPoint?.address.toString()
            tvPointB.text = load.bPoint?.address.toString()
            tvCustomer.text = "Customer: ${load.customer ?: "Unknown"}"
            tvDeadline.text = "Deadline: ${load.deadline}"
            tvDescription.text = "Description: ${load.description}"
            tvStatus.text = "Status: ${load.status}"
            tvAcceptedTime.text = "Accepted time: ${load.acceptedTime.getDate()}"
            tvCompletedTime.text = "Completed time:  ${load.completedTime.getDate()}"
            when (load.status) {
                Constants.NEW -> {
                    if (prefs.userType == Constants.DRIVER_USER_TYPE) {
                        containerDriver.visibility = View.GONE
                        btnFinishOrder.visibility = View.GONE
                        btnCallDriver.visibility = View.GONE
                        binding.containerTimes.visibility = View.GONE
                    } else {
                        containerDriver.visibility = View.GONE
                        btnAcceptOrder.visibility = View.GONE
                        btnFinishOrder.visibility = View.GONE
                        btnCallDriver.visibility = View.GONE
                        binding.containerTimes.visibility = View.GONE
                    }
                }
                Constants.ACTIVE -> {
                    if (prefs.userType == Constants.DRIVER_USER_TYPE) {
                        btnAcceptOrder.visibility = View.GONE
                        btnCallDriver.visibility = View.GONE
                        containerDriver.visibility = View.GONE
                    } else {
                        btnAcceptOrder.visibility = View.GONE
                        btnFinishOrder.visibility = View.GONE
                    }
                }
                Constants.COMPLETED -> {
                    if (prefs.userType == Constants.DRIVER_USER_TYPE) {
                        btnAcceptOrder.visibility = View.GONE
                        btnCallDriver.visibility = View.GONE
                        containerDriver.visibility = View.GONE
                        btnFinishOrder.visibility = View.GONE
                    } else {
                        btnAcceptOrder.visibility = View.GONE
                        btnFinishOrder.visibility = View.GONE
                    }
                }
            }
        }
    }

    private fun setDriverData(driver: Driver) {
        with(binding) {
            tvDriverName.text = driver.fio
            tvDriverCar.text = driver.car
            tvDriverPhone.text = driver.phoneNumber
        }
        abstractMap?.addMarker(
            AbstractMarkerOptions<Any>(AbstractPosition(driver.lat ?: 0.0, driver.lng ?: 0.0)).apply {
                icon = R.drawable.ic_pin_busy
            }
        )
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
        binding.tvEta.text = "Eta: ${route.duration.toMinutes() / 60} hours ${route.duration.toMinutes() % 60} minute"
        binding.tvLength.text = "Length: ${route.lengthInMeters * 0.001} km"
        val polyline = AbstractPolylineOptions()
        route.geometry.vertices.forEach {
            Log.d("dataRoad", "addPolyline: ${it.latitude} ${it.longitude}")
            polyline.points.add(AbstractPosition(it.latitude, it.longitude))
            polyline.width = 15f
        }
        abstractMap?.addPolyline(polyline)
    }
}
