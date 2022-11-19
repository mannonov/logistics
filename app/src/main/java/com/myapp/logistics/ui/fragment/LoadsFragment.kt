package com.myapp.logistics.ui.fragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.myapp.logistics.R
import com.myapp.logistics.databinding.FragmentLoadsBinding
import com.myapp.logistics.ui.viewmodel.AdminViewModel
import com.myapp.logistics.util.onClick
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoadsFragment : Fragment(R.layout.fragment_loads) {

    private val binding: FragmentLoadsBinding by viewBinding(FragmentLoadsBinding::bind)
    private val adminVM by viewModels<AdminViewModel>({ requireParentFragment() })

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnAddLoad.onClick {
            findNavController().navigate(LoadsFragmentDirections.actionLoadsFragmentToAddLoadFragment())
        }
    }

    override fun onResume() {
        super.onResume()
        adminVM.botNavUIState.value = true
    }
}
