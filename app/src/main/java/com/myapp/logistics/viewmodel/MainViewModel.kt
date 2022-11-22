package com.myapp.logistics.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.myapp.logistics.map.AbstractPosition
import com.myapp.logistics.model.Driver
import com.myapp.logistics.repository.MainRepository
import com.myapp.logistics.util.Outcome
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val repository: MainRepository) : ViewModel() {

    private val _lastLocation = MutableStateFlow<Outcome<AbstractPosition>>(Outcome.loading())
    val lastLocation = _lastLocation.asStateFlow()

    fun getLastKnownLocation() {
        viewModelScope.launch {
            val result = repository.getLastLocation()
            result.onSuccess {
                _lastLocation.emit(Outcome.success(it))
            }
            result.onFailure {
                _lastLocation.emit(Outcome.failure(it))
            }
        }
    }

    fun updateDriverLocation(driver: Driver, abstractPosition: AbstractPosition) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.sendDriverLastLocation(driver, abstractPosition)
        }
    }
}
