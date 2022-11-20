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
import com.myapp.logistics.databinding.FragmentAddLoadBinding
import com.myapp.logistics.dialog.EnterPointDialog
import com.myapp.logistics.model.Load
import com.myapp.logistics.model.Point
import com.myapp.logistics.util.Outcome
import com.myapp.logistics.util.addRepeatingJob
import com.myapp.logistics.util.onClick
import com.myapp.logistics.viewmodel.AddLoadViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.withContext

private const val POINT_A = 0
private const val POINT_B = 1

@AndroidEntryPoint
class AddLoadFragment : BottomSheetDialogFragment() {

    private var binding: FragmentAddLoadBinding? = null
    private var pointA: Point? = null
    private var pointB: Point? = null
    private val viewModel by viewModels<AddLoadViewModel>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_add_load, container, false)
        binding = FragmentAddLoadBinding.bind(view)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding?.btnLoad?.onClick {
            getLoadDataFromUI()
        }
        binding?.tieAPoint?.onClick {
            showPointDialog(POINT_A)
        }
        binding?.tieBPoint?.onClick {
            showPointDialog(POINT_B)
        }
        viewLifecycleOwner.addRepeatingJob(Lifecycle.State.CREATED) {
            viewModel.addLoadFlow.collectLatest { value: Outcome<Boolean> ->
                when (value) {
                    is Outcome.Progress -> withContext(Dispatchers.Main) {
                        if (value.loading) {
                            Toast.makeText(requireContext(), "Loading...", Toast.LENGTH_SHORT).show()
                        }
                    }
                    is Outcome.Success -> withContext(Dispatchers.Main) {
                        Toast.makeText(requireContext(), "Success...", Toast.LENGTH_SHORT).show()
                        setFragmentResult(this@AddLoadFragment.javaClass.simpleName, bundleOf("add_load" to "success"))
                        findNavController().popBackStack()
                    }
                    is Outcome.Failure -> withContext(Dispatchers.Main) {
                        Toast.makeText(requireContext(), "Failure...", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }

    private fun showPointDialog(point: Int) {
        EnterPointDialog(requireContext()).also { it ->
            it.setAddOnPointListener(
                addOnPointListener = EnterPointDialog.PointListener { selected_point ->
                    when (point) {
                        POINT_A -> {
                            pointA = selected_point
                            binding?.tieAPoint?.setText(selected_point.toString())
                        }
                        POINT_B -> {
                            pointB = selected_point
                            binding?.tieBPoint?.setText(selected_point.toString())
                        }
                    }
                }
            )
        }.show()
    }

    private fun getLoadDataFromUI() {
        val load = Load()
        if (pointB == null || pointA == null) {
            Toast.makeText(requireContext(), "Please enter required fields", Toast.LENGTH_SHORT).show()
            return
        }
        load.bPoint = pointB
        load.aPoint = pointA
        binding?.apply {
            load.customer = tieCustomer.text.toString()
            load.deadline = tieDeadline.text.toString()
            load.description = tieDescription.text.toString()
        }
        viewModel.addLoad(load = load)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}
