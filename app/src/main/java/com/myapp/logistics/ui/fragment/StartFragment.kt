package com.myapp.logistics.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.google.firebase.auth.FirebaseAuth
import com.myapp.logistics.R
import com.myapp.logistics.databinding.FragmentStartBinding
import com.myapp.logistics.util.Constants
import com.myapp.logistics.util.LogisticsPref
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class StartFragment : Fragment(R.layout.fragment_start) {

    private val binding: FragmentStartBinding by viewBinding(FragmentStartBinding::bind)

    @Inject
    lateinit var auth: FirebaseAuth

    @Inject
    lateinit var prefs: LogisticsPref

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        when (prefs.userType) {
            Constants.DEFAULT_USER_TYPE -> findNavController().navigate(StartFragmentDirections.actionStartFragmentToSignInFragment())
            Constants.ADMIN_USER_TYPE -> findNavController().navigate(StartFragmentDirections.actionStartFragmentToAdminFragment())
            Constants.DRIVER_USER_TYPE -> Unit
        }
    }
}
