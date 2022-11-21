package com.myapp.logistics.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.myapp.logistics.model.Driver
import com.myapp.logistics.repository.SignInRepository
import com.myapp.logistics.util.Outcome
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor(private val repository: SignInRepository) : ViewModel() {

    private val _driverFlow = MutableStateFlow<Outcome<Driver>>(Outcome.loading(isLoading = false))
    val driverFlow = _driverFlow.asStateFlow()

    fun getDriverData(userName: String, password: String) {
        viewModelScope.launch(Dispatchers.IO) {
            _driverFlow.value = Outcome.loading(true)
            repository.getDriverData(userName, password) {
                _driverFlow.value = Outcome.success(it)
            }
        }
    }
}
