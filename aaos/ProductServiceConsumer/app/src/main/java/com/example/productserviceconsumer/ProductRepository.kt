package com.example.productserviceconsumer

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class ProductRepository(
    private val connector: ProductServiceConnector,
) {

    fun getProducts(): Flow<List<Product?>> = flow {
        try {
            val products = connector.fetchProducts()
            emit(products)
        } catch (e: Exception) {
            throw RuntimeException("Failed to fetch products: ${e.message}", e)
        }
    }

}