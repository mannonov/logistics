package com.myapp.logistics.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.navigation.fragment.findNavController
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.myapp.logistics.R
import com.myapp.logistics.databinding.FragmentAddDriverBinding
import com.myapp.logistics.model.Driver
import com.myapp.logistics.util.Outcome
import com.myapp.logistics.util.addRepeatingJob
import com.myapp.logistics.util.onClick
import com.myapp.logistics.viewmodel.AddDriverViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.withContext

@AndroidEntryPoint
class AddDriverFragment : BottomSheetDialogFragment() {

    private var binding: FragmentAddDriverBinding? = null
    private val viewModel by viewModels<AddDriverViewModel>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_add_driver, container, false)
        binding = FragmentAddDriverBinding.bind(view)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewLifecycleOwner.addRepeatingJob(Lifecycle.State.CREATED) {
            viewModel.addDriverFlow.collectLatest { value: Outcome<Boolean> ->
                when (value) {
                    is Outcome.Progress -> withContext(Dispatchers.Main) {
                        if (value.loading) {
                            Toast.makeText(requireContext(), "Loading...", Toast.LENGTH_SHORT).show()
                        }
                    }
                    is Outcome.Success -> withContext(Dispatchers.Main) {
                        Toast.makeText(requireContext(), "Success...", Toast.LENGTH_SHORT).show()
                        setFragmentResult(this@AddDriverFragment.javaClass.simpleName, bundleOf("add_load" to "success"))
                        findNavController().popBackStack()
                    }
                    is Outcome.Failure -> withContext(Dispatchers.Main) {
                        Toast.makeText(requireContext(), "Failure...", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
        binding?.btnAddDriver?.onClick {
            val driver = getDriver()
            viewModel.addDriver(driver)
        }
    }

    private fun getDriver(): Driver {
        return Driver(
            id = null,
            fio = binding?.tieFio?.text.toString(),
            car = binding?.tieCar?.text.toString(),
            phoneNumber = binding?.tiePhoneNumber?.text.toString(),
            userName = binding?.tieUserName?.text.toString(),
            password = binding?.tiePassword?.text.toString()
        )
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }
}
