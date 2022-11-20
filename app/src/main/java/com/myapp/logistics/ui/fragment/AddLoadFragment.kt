package com.myapp.logistics.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.myapp.logistics.R
import com.myapp.logistics.databinding.FragmentAddLoadBinding
import com.myapp.logistics.dialog.EnterPointDialog
import com.myapp.logistics.model.Load
import com.myapp.logistics.model.Point
import com.myapp.logistics.util.onClick
import dagger.hilt.android.AndroidEntryPoint

private const val POINT_A = 0
private const val POINT_B = 1

@AndroidEntryPoint
class AddLoadFragment : BottomSheetDialogFragment() {

    private var binding: FragmentAddLoadBinding? = null
    private var pointA: Point? = null
    private var pointB: Point? = null

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
        binding?.apply {
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}
