package com.myapp.logistics.ui.fragment

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import by.kirich1409.viewbindingdelegate.viewBinding
import com.myapp.logistics.R
import com.myapp.logistics.databinding.FragmentProfileBinding
import com.myapp.logistics.ui.MainActivity
import com.myapp.logistics.util.Constants
import com.myapp.logistics.util.LogisticsPref
import com.myapp.logistics.util.onClick
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class ProfileFragment : Fragment(R.layout.fragment_profile) {

    private val binding: FragmentProfileBinding by viewBinding(FragmentProfileBinding::bind)

    @Inject
    lateinit var prefs: LogisticsPref

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnLogOutAdmin.onClick {
            prefs.clearSharedPref()
            val intent = Intent(requireActivity(), MainActivity::class.java)
            requireActivity().startActivity(intent)
            requireActivity().finish()
        }
        if (prefs.userType == Constants.DRIVER_USER_TYPE) {
            with(binding) {
                tvDriverName.text = prefs.driver.fio
                tvDriverCar.text = prefs.driver.car
                tvDriverPhone.text = prefs.driver.phoneNumber
                tvUserName.text = "Username: ${prefs.driver.userName}"
                tvUserId.text = "User ID: ${prefs.driver.id}"
            }
        }
    }
}
