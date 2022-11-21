package com.myapp.logistics.ui.fragment

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResultListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.myapp.logistics.R
import com.myapp.logistics.adapter.DriversAdapter
import com.myapp.logistics.databinding.FragmentDriversBinding
import com.myapp.logistics.model.Driver
import com.myapp.logistics.util.Outcome
import com.myapp.logistics.util.addRepeatingJob
import com.myapp.logistics.util.onClick
import com.myapp.logistics.viewmodel.DriversViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.withContext

@AndroidEntryPoint
class DriversFragment : Fragment(R.layout.fragment_drivers) {

    private val binding: FragmentDriversBinding by viewBinding(FragmentDriversBinding::bind)
    private val viewModel by viewModels<DriversViewModel>()
    private val driversAdapter: DriversAdapter = DriversAdapter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnAddDriver.onClick {
            findNavController().navigate(DriversFragmentDirections.actionDriversFragment2ToAddDriverFragment())
        }
        setFragmentResultListener(AddDriverFragment::class.java.simpleName) { request, bundle ->
            viewModel.getDrivers()
        }
        binding.rvDrivers.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = driversAdapter
        }
        viewModel.getDrivers()
        viewLifecycleOwner.addRepeatingJob(Lifecycle.State.CREATED) {
            viewModel.driversFlow.collectLatest {
                when (it) {
                    is Outcome.Progress -> withContext(Dispatchers.Main) {
                        Toast.makeText(requireContext(), "Loading...", Toast.LENGTH_SHORT).show()
                    }
                    is Outcome.Failure -> withContext(Dispatchers.Main) {
                        Toast.makeText(requireContext(), "Failure...", Toast.LENGTH_SHORT).show()
                    }
                    is Outcome.Success -> withContext(Dispatchers.Main) {
                        Toast.makeText(requireContext(), "Success...", Toast.LENGTH_SHORT).show()
                        setDriversData(it.data)
                    }
                }
            }
        }
    }

    private fun setDriversData(list: List<Driver>) {
        driversAdapter.submitList(list)
    }
}
