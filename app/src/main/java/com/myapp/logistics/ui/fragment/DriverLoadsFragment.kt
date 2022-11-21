package com.myapp.logistics.ui.fragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import by.kirich1409.viewbindingdelegate.viewBinding
import com.myapp.logistics.R
import com.myapp.logistics.databinding.FragmentDriverLoadsBinding

class DriverLoadsFragment : Fragment(R.layout.fragment_driver_loads) {

    private val binding: FragmentDriverLoadsBinding by viewBinding(FragmentDriverLoadsBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
}
