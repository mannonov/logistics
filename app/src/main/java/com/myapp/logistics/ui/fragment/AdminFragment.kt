package com.myapp.logistics.ui.fragment

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.myapp.logistics.R
import com.myapp.logistics.databinding.FragmentAdminBinding
import com.myapp.logistics.ui.viewmodel.AdminViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AdminFragment : Fragment(R.layout.fragment_admin) {

    private val binding: FragmentAdminBinding by viewBinding(FragmentAdminBinding::bind)
    private val viewModel by viewModels<AdminViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.botNavUIState.observe(viewLifecycleOwner, this::subscribeBottomNavViewState)
        setUpNavigation()
    }

    private fun setUpNavigation() {
        val navHostFragment = childFragmentManager
            .findFragmentById(R.id.container_nav_host) as NavHostFragment
        val navController = navHostFragment.navController
        binding.navigationBar.setupWithNavController(navController)
    }

    private fun subscribeBottomNavViewState(boolean: Boolean) {
        Toast.makeText(requireContext(), "$boolean", Toast.LENGTH_SHORT).show()
        binding.navigationBar.visibility = if (boolean) View.VISIBLE else View.GONE
    }
}
