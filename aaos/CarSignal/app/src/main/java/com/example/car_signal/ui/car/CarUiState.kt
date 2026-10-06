package com.example.car_signal.ui.car

sealed class CarUiState {
    object Loading : CarUiState()
    data class Success(val speed: Float, val gear: Int): CarUiState()
    data class Error(val message: String): CarUiState()
    object PermissionRequired : CarUiState()
}