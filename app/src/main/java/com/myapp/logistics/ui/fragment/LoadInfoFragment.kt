package com.myapp.logistics.ui.fragment

import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import by.kirich1409.viewbindingdelegate.viewBinding
import com.myapp.logistics.R
import com.myapp.logistics.databinding.FragmentLoadInfoBinding

class LoadInfoFragment : Fragment(R.layout.fragment_load_info) {

    private val binding: FragmentLoadInfoBinding by viewBinding(FragmentLoadInfoBinding::bind)
    private var abstractMap: com.myapp.logistics.map.AbstractMap? = null
    private val args: LoadInfoFragmentArgs by navArgs()
}
