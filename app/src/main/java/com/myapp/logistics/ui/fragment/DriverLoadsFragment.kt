package com.myapp.logistics.ui.fragment

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.myapp.logistics.R
import com.myapp.logistics.adapter.LoadsAdapter
import com.myapp.logistics.databinding.FragmentDriverLoadsBinding
import com.myapp.logistics.model.Load
import com.myapp.logistics.util.LogisticsPref
import com.myapp.logistics.util.Outcome
import com.myapp.logistics.util.addRepeatingJob
import com.myapp.logistics.util.onTabSelected
import com.myapp.logistics.viewmodel.DriverLoadsViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.withContext
import javax.inject.Inject

@AndroidEntryPoint
class DriverLoadsFragment : Fragment(R.layout.fragment_driver_loads) {

    private val binding: FragmentDriverLoadsBinding by viewBinding(FragmentDriverLoadsBinding::bind)

    @Inject
    lateinit var prefs: LogisticsPref

    private val viewModel by viewModels<DriverLoadsViewModel>()

    private val loadsAdapter: LoadsAdapter = LoadsAdapter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getNewLoads()
        binding.rvLoads.apply {
            adapter = loadsAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }
        binding.loadsTab.onTabSelected { position: Int ->
            when (position) {
                0 -> {
                    viewModel.getNewLoads()
                }
                1 -> {
                    viewModel.getActiveLoads(prefs.driver.id.toString())
                }
                2 -> {
                    viewModel.getCompletedLoads(prefs.driver.id.toString())
                }
            }
        }
        viewLifecycleOwner.addRepeatingJob(Lifecycle.State.CREATED) {
            viewModel.loadFlow.collectLatest {
                when (it) {
                    is Outcome.Progress -> withContext(Dispatchers.Main) {
                        Toast.makeText(requireContext(), "Loading...", Toast.LENGTH_SHORT).show()
                    }
                    is Outcome.Failure -> withContext(Dispatchers.Main) {
                        Toast.makeText(requireContext(), "Failure...", Toast.LENGTH_SHORT).show()
                    }
                    is Outcome.Success -> withContext(Dispatchers.Main) {
                        Toast.makeText(requireContext(), "Success...", Toast.LENGTH_SHORT).show()
                        setAdapterData(it.data)
                    }
                }
            }
        }
    }

    private fun setAdapterData(list: List<Load>) {
        loadsAdapter.submitList(list)
    }
}
