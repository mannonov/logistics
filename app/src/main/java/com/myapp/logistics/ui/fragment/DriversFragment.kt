package com.myapp.logistics.ui.fragment

import androidx.fragment.app.Fragment
import by.kirich1409.viewbindingdelegate.viewBinding
import com.myapp.logistics.R
import com.myapp.logistics.databinding.FragmentDriversBinding

class DriversFragment : Fragment(R.layout.fragment_drivers) {

    private val binding: FragmentDriversBinding by viewBinding(FragmentDriversBinding::bind)
}
