package com.myapp.logistics.ui.fragment

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.myapp.logistics.R
import com.myapp.logistics.databinding.FragmentSignInBinding
import com.myapp.logistics.util.*
import com.myapp.logistics.viewmodel.SignInViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.withContext
import javax.inject.Inject

@AndroidEntryPoint
class SignInFragment : Fragment(R.layout.fragment_sign_in) {

    private val binding: FragmentSignInBinding by viewBinding(FragmentSignInBinding::bind)

    @Inject
    lateinit var prefs: LogisticsPref

    private val viewModel by viewModels<SignInViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            btnSignIn.onClick {
                checkInputData(tieLogin.text.toString(), tiePassword.text.toString())
            }
        }
        viewLifecycleOwner.addRepeatingJob(Lifecycle.State.CREATED) {
            viewModel.driverFlow.collectLatest {
                when (it) {
                    is Outcome.Progress -> withContext(Dispatchers.Main) {
                        Toast.makeText(requireContext(), "Loading...", Toast.LENGTH_SHORT).show()
                    }
                    is Outcome.Failure -> withContext(Dispatchers.Main) {
                        Toast.makeText(requireContext(), "Failure...", Toast.LENGTH_SHORT).show()
                    }
                    is Outcome.Success -> withContext(Dispatchers.Main) {
                        Toast.makeText(requireContext(), "Success...", Toast.LENGTH_SHORT).show()
                        prefs.driver = it.data
                        prefs.userType = Constants.DRIVER_USER_TYPE
                        findNavController().navigate(SignInFragmentDirections.actionSignInFragmentToDriverFragment())
                    }
                }
            }
        }
    }

    private fun checkInputData(login: String, password: String) {
        if (login == RemoteConfigUtils.getAdminLogin() && password == RemoteConfigUtils.getAdminPassword()) {
            prefs.userType = Constants.ADMIN_USER_TYPE
            findNavController().navigate(SignInFragmentDirections.actionSignInFragmentToAdminFragment())
        } else {
            viewModel.getDriverData(login, password)
        }
    }
}
