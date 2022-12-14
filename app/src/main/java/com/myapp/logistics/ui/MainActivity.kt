package com.myapp.logistics.ui

import android.Manifest
import android.os.Bundle
import android.os.Handler
import android.util.Log
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import com.myapp.logistics.R
import com.myapp.logistics.util.Constants
import com.myapp.logistics.util.LogisticsPref
import com.myapp.logistics.util.Outcome
import com.myapp.logistics.util.addRepeatingJob
import com.myapp.logistics.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val viewModel by viewModels<MainViewModel>()

    private lateinit var runnable: Runnable

    @Inject
    lateinit var prefs: LogisticsPref

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        addRepeatingJob(Lifecycle.State.STARTED) {
            viewModel.lastLocation.collect { outcome ->
                if (outcome is Outcome.Success) {
                    viewModel.updateDriverLocation(prefs.driver, outcome.data)
                }
            }
        }
        requestMultiplePermissions.launch(
            arrayOf(
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION
            )
        )
        runnable = Runnable {
            if (prefs.userType == Constants.DRIVER_USER_TYPE) {
                viewModel.getLastKnownLocation()
                Log.d("locationFe", "onCreate: running")
            }
            Handler().postDelayed(runnable, 5000)
        }
        runnable.run()
        Handler().postDelayed(runnable, 5000)
    }
    private val requestMultiplePermissions =
        registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { permissions ->
            permissions.entries.forEach {
                Log.e("LOG_TAG", "${it.key} = ${it.value}")
            }
        }
}
