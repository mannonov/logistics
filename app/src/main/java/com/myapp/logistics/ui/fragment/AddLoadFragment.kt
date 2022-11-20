package com.myapp.logistics.ui.fragment

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import by.kirich1409.viewbindingdelegate.viewBinding
import com.myapp.logistics.R
import com.myapp.logistics.databinding.FragmentAddLoadBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddLoadFragment : Fragment(R.layout.fragment_add_load) {

    private val binding: FragmentAddLoadBinding by viewBinding(FragmentAddLoadBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Toast.makeText(requireContext(), "${parentFragment?.javaClass?.simpleName}", Toast.LENGTH_SHORT).show()
    }

    override fun onResume() {
        super.onResume()
    }
}
