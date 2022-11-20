package com.myapp.logistics.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.myapp.logistics.model.Load
import com.myapp.logistics.repository.AddLoadRepository
import com.myapp.logistics.util.Outcome
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddLoadViewModel @Inject constructor(private val repository: AddLoadRepository) : ViewModel() {

    private val _addLoadFlow = MutableStateFlow<Outcome<Boolean>>(Outcome.loading())
    val addLoadFlow = _addLoadFlow.asStateFlow()

    fun addLoad(load: Load) {
        viewModelScope.launch(Dispatchers.IO) {
            _addLoadFlow.emit(Outcome.loading(true))
            repository.addLoadFirebase(load = load) {
                if (it) {
                    _addLoadFlow.value = Outcome.success(true)
                } else {
                    _addLoadFlow.value = Outcome.failure(Throwable("Something went wrong"))
                }
            }
        }
    }
}
