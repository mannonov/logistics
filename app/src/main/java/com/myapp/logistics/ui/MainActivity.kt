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
import com.myapp.logistics.util.Outcome
import com.myapp.logistics.util.addRepeatingJob
import com.myapp.logistics.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val viewModel by viewModels<MainViewModel>()

    private lateinit var runnable: Runnable

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        addRepeatingJob(Lifecycle.State.STARTED) {
            viewModel.lastLocation.collect { outcome ->
                if (outcome is Outcome.Success) {
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
            viewModel.getLastKnownLocation()
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
