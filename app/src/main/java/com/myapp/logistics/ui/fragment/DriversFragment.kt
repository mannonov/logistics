package com.myapp.logistics.ui.fragment

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResultListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.myapp.logistics.R
import com.myapp.logistics.databinding.FragmentDriversBinding
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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnAddDriver.onClick {
            findNavController().navigate(DriversFragmentDirections.actionDriversFragment2ToAddDriverFragment())
        }
        setFragmentResultListener(AddDriverFragment::class.java.simpleName) { request, bundle ->
            viewModel.getDrivers()
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
                        Toast.makeText(requireContext(), "Success... ${it.data}", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }
}
