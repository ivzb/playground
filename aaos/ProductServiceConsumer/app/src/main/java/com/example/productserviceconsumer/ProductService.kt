package com.example.productserviceconsumer

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.os.RemoteCallbackList
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.random.Random

class ProductService : Service() {

    private val callbacks = RemoteCallbackList<IProductCallback>()

    val binder = object: IProductInterface.Stub() {

        override fun fetchProducts(callback: IProductCallback?) {
            callbacks.register(callback)

            CoroutineScope(Dispatchers.IO).launch {
                try {
                    val products = fetchProducts()
                    callback?.onSuccess(products)
                } catch (e: Exception) {
                    callback?.onError(e.message ?: "Unknown error")
                }
            }
        }

        override fun unregisterCallback(callback: IProductCallback?) {
            callbacks.unregister(callback)
        }
    }

    override fun onBind(intent: Intent): IBinder {
        return binder
    }

    private suspend fun fetchProducts(): List<Product> {
        delay(500)

        val rand = Random

        if (rand.nextInt() % 3 == 0) {
            throw RuntimeException("Bad luck")
        }

        return listOf(
            Product(rand.nextInt(), "Product ${rand.nextInt()}"),
            Product(rand.nextInt(), "Product ${rand.nextInt()}"),
            Product(rand.nextInt(), "Product ${rand.nextInt()}"),
        )
    }

}