package com.example.productserviceconsumer

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class UserViewModel(private val productRepository: ProductRepository): ViewModel() {

    private val _uiState = MutableStateFlow<UserUiState>(UserUiState.Success(emptyList()))
    val uiState: StateFlow<UserUiState> = _uiState.asStateFlow()

    private val _uiEffect = Channel<UserUiEffect>(Channel.BUFFERED)
    val uiEffect = _uiEffect.receiveAsFlow()

    fun fetchProducts() {
        viewModelScope.launch {
            _uiState.value = UserUiState.Loading

            productRepository.getProducts()
                .catch { e ->
                    _uiState.value = UserUiState.Error(e.message ?: "Unknown error")
                    _uiEffect.send(UserUiEffect.ShowToast("Failed to fetch products: ${e.message}"))
                }
                .collect { products ->
                    _uiState.value = UserUiState.Success(products)
                }
        }
    }

}