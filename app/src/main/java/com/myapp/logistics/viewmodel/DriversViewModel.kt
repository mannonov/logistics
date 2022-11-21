package com.myapp.logistics.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.myapp.logistics.model.Driver
import com.myapp.logistics.repository.DriversRepository
import com.myapp.logistics.util.Outcome
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DriversViewModel @Inject constructor(private val repository: DriversRepository) : ViewModel() {

    private val _driversFlow = MutableStateFlow<Outcome<List<Driver>>>(Outcome.loading(isLoading = false))
    val driversFlow = _driversFlow.asStateFlow()

    fun getDrivers() {
        viewModelScope.launch(Dispatchers.IO) {
            _driversFlow.value = Outcome.loading(true)
            repository.getDrivers {
                _driversFlow.value = Outcome.success(it)
            }
        }
    }
}
