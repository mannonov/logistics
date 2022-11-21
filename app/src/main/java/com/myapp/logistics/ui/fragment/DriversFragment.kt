package com.myapp.logistics.ui.fragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.myapp.logistics.R
import com.myapp.logistics.databinding.FragmentDriversBinding
import com.myapp.logistics.util.onClick

class DriversFragment : Fragment(R.layout.fragment_drivers) {

    private val binding: FragmentDriversBinding by viewBinding(FragmentDriversBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnAddDriver.onClick {
            findNavController().navigate(DriversFragmentDirections.actionDriversFragment2ToAddDriverFragment())
        }
    }
}
