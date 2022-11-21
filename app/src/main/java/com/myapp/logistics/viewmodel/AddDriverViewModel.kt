package com.myapp.logistics.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.myapp.logistics.model.Driver
import com.myapp.logistics.repository.AddDriverRepository
import com.myapp.logistics.util.Outcome
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddDriverViewModel @Inject constructor(private val repository: AddDriverRepository) : ViewModel() {

    private val _addDriverFlow = MutableStateFlow<Outcome<Boolean>>(Outcome.loading())
    val addDriverFlow = _addDriverFlow.asStateFlow()

    fun addDriver(driver: Driver) {
        viewModelScope.launch(Dispatchers.IO) {
            _addDriverFlow.emit(Outcome.loading(true))
            repository.addLoadFirebase(driver = driver) {
                if (it) {
                    _addDriverFlow.value = Outcome.success(true)
                } else {
                    _addDriverFlow.value = Outcome.failure(Throwable("Something went wrong"))
                }
            }
        }
    }
}
