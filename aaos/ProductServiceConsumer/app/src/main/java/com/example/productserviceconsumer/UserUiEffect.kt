package com.example.productserviceconsumer

sealed interface UserUiEffect {
    data class ShowToast(val message: String): UserUiEffect
}