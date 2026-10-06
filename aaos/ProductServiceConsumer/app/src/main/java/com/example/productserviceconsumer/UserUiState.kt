package com.example.productserviceconsumer

sealed interface UserUiState {
    object Loading: UserUiState
    data class Success(val products: List<Product?>): UserUiState
    data class Error(val message: String): UserUiState
}