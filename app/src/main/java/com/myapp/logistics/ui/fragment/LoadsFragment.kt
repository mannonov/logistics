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
import com.myapp.logistics.databinding.FragmentLoadsBinding
import com.myapp.logistics.util.Outcome
import com.myapp.logistics.util.addRepeatingJob
import com.myapp.logistics.util.onClick
import com.myapp.logistics.viewmodel.LoadsViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.withContext

@AndroidEntryPoint
class LoadsFragment : Fragment(R.layout.fragment_loads) {

    private val binding: FragmentLoadsBinding by viewBinding(FragmentLoadsBinding::bind)
    private val viewModel by viewModels<LoadsViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnAddLoad.onClick {
            findNavController().navigate(LoadsFragmentDirections.actionLoadsFragmentToAddLoadFragment())
        }
        setFragmentResultListener(AddLoadFragment::class.java.simpleName) { request, bundle ->
            viewModel.getNewLoads()
        }
        viewModel.getNewLoads()
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
                    }
                }
            }
        }
    }
}
