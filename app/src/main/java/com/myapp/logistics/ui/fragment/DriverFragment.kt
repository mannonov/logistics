package com.myapp.logistics.ui.fragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.myapp.logistics.R
import com.myapp.logistics.databinding.FragmentDriverBinding

class DriverFragment : Fragment(R.layout.fragment_driver) {

    private val binding: FragmentDriverBinding by viewBinding(FragmentDriverBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpNavigation()
    }

    private fun setUpNavigation() {
        val navHostFragment = childFragmentManager
            .findFragmentById(R.id.container_nav_host) as NavHostFragment
        val navController = navHostFragment.navController
        binding.navigationBar.setupWithNavController(navController)
        navController.addOnDestinationChangedListener { controller, destination, arguments ->
            when (destination.id) {
                R.id.loadInfoFragment -> showBottomNavigation(false)
                else -> showBottomNavigation(true)
            }
        }
    }

    private fun showBottomNavigation(boolean: Boolean) {
        binding.navigationBar.visibility = if (boolean) View.VISIBLE else View.GONE
    }
}
