package com.example.car_signal.ui.car

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.car_signal.data.CarRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.stateIn

class CarViewModel(carRepository: CarRepository): ViewModel() {

    private val speedPermissionGranted = MutableStateFlow<Boolean?>(null)

    val uiState: StateFlow<CarUiState> = speedPermissionGranted
        .filterNotNull()
        .flatMapLatest { granted ->
            if (!granted) {
                flowOf(CarUiState.PermissionRequired)
            } else {
                combine<Float, Int, CarUiState>(
                    carRepository.speedFlow(),
                    carRepository.gearFlow()
                ) { speed, gear -> CarUiState.Success(speed, gear) }
                .catch { e ->
                    emit(CarUiState.Error(e.message ?: "Something went wrong."))
                }
            }
        }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), CarUiState.Loading)

    fun onPermissionChanged(granted: Boolean) {
        speedPermissionGranted.value = granted
    }

}