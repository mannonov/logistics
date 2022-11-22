package com.myapp.logistics.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.here.sdk.routing.Route
import com.myapp.logistics.map.AbstractPosition
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
}
