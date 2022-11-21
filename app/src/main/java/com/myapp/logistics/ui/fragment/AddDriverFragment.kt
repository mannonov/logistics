package com.myapp.logistics.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.myapp.logistics.R
import com.myapp.logistics.databinding.FragmentAddDriverBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddDriverFragment : BottomSheetDialogFragment() {

    private var binding: FragmentAddDriverBinding? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_add_driver, container, false)
        binding = FragmentAddDriverBinding.bind(view)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }
}
