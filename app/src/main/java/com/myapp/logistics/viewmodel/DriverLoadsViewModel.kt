package com.myapp.logistics.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.myapp.logistics.model.Load
import com.myapp.logistics.repository.DriverLoadsRepository
import com.myapp.logistics.util.Outcome
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DriverLoadsViewModel @Inject constructor(private val repository: DriverLoadsRepository) : ViewModel() {

    private val _loadFlow = MutableStateFlow<Outcome<List<Load>>>(Outcome.loading(isLoading = false))
    val loadFlow = _loadFlow.asStateFlow()

    fun getNewLoads() {
        viewModelScope.launch(Dispatchers.IO) {
            _loadFlow.emit(Outcome.loading(isLoading = true))
            repository.getNewLoads {
                _loadFlow.value = Outcome.success(it)
            }
        }
    }

    fun getActiveLoads(id: String) {
        viewModelScope.launch(Dispatchers.IO) {
            _loadFlow.emit(Outcome.loading(isLoading = true))
            repository.getActiveLoads(id) {
                _loadFlow.value = Outcome.success(it)
            }
        }
    }

    fun getCompletedLoads(id: String) {
        viewModelScope.launch(Dispatchers.IO) {
            _loadFlow.emit(Outcome.loading(isLoading = true))
            repository.getCompletedLoads(id) {
                _loadFlow.value = Outcome.success(it)
            }
        }
    }
}
