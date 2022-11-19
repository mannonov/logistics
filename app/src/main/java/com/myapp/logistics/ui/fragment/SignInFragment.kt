package com.myapp.logistics.ui.fragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.myapp.logistics.R
import com.myapp.logistics.databinding.FragmentSignInBinding
import com.myapp.logistics.util.Constants
import com.myapp.logistics.util.LogisticsPref
import com.myapp.logistics.util.RemoteConfigUtils
import com.myapp.logistics.util.onClick
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class SignInFragment : Fragment(R.layout.fragment_sign_in) {

    private val binding: FragmentSignInBinding by viewBinding(FragmentSignInBinding::bind)

    @Inject
    lateinit var prefs: LogisticsPref

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            btnSignIn.onClick {
                checkInputData(tieLogin.text.toString(), tiePassword.text.toString())
            }
        }
    }

    private fun checkInputData(login: String, password: String) {
        if (login == RemoteConfigUtils.getAdminLogin() && password == RemoteConfigUtils.getAdminPassword()) {
            prefs.userType = Constants.ADMIN_USER_TYPE
            findNavController().navigate(SignInFragmentDirections.actionSignInFragmentToAdminFragment())
        }
    }
}
