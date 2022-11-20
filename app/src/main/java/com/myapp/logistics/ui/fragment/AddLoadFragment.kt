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
import com.myapp.logistics.util.onClick
import dagger.hilt.android.AndroidEntryPoint

private const val POINT_A = 0
private const val POINT_B = 1

@AndroidEntryPoint
class AddLoadFragment : BottomSheetDialogFragment() {

    private var binding: FragmentAddLoadBinding? = null

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
        EnterPointDialog(requireContext()).show()
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
