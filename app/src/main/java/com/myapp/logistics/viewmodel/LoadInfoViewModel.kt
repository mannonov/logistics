package com.myapp.logistics.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.here.sdk.routing.Route
import com.myapp.logistics.map.AbstractPosition
import com.myapp.logistics.model.Load
import com.myapp.logistics.repository.LoadInfoRepository
import com.myapp.logistics.util.Outcome
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoadInfoViewModel @Inject constructor(private val repository: LoadInfoRepository) : ViewModel() {

    private val _routeFlow = MutableStateFlow<Outcome<Route>>(Outcome.loading())
    val routeFlow = _routeFlow.asStateFlow()

    fun getRoute(a: AbstractPosition, b: AbstractPosition) {
        viewModelScope.launch(Dispatchers.IO) {
            _routeFlow.value = Outcome.loading()
            repository.getRoute(a, b) {
                _routeFlow.value = it
            }
        }
    }

    private val _acceptLoadFlow = MutableStateFlow<Outcome<Boolean>>(Outcome.loading())
    val acceptLoadFlow = _acceptLoadFlow.asStateFlow()

    fun acceptLoad(load: Load, driverId: String) {
        viewModelScope.launch(Dispatchers.IO) {
            _acceptLoadFlow.emit(Outcome.loading(true))
            repository.acceptOrder(load, driverId) {
                if (it) {
                    _acceptLoadFlow.value = Outcome.success(true)
                } else {
                    _acceptLoadFlow.value = Outcome.failure(Throwable("Something went wrong"))
                }
            }
        }
    }

    private val _finishLoadFlow = MutableStateFlow<Outcome<Boolean>>(Outcome.loading())
    val finishLoadFlow = _finishLoadFlow.asStateFlow()

    fun finishLoad(load: Load) {
        viewModelScope.launch(Dispatchers.IO) {
            _acceptLoadFlow.emit(Outcome.loading(true))
            repository.finishOrder(load) {
                if (it) {
                    _acceptLoadFlow.value = Outcome.success(true)
                } else {
                    _acceptLoadFlow.value = Outcome.failure(Throwable("Something went wrong"))
                }
            }
        }
    }
}
