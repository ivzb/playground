package com.example.productserviceconsumer

import android.content.ComponentName
import android.content.Context
import android.content.Context.BIND_AUTO_CREATE
import android.content.Intent
import android.content.ServiceConnection
import android.os.IBinder
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

class ProductServiceConnector(private val context: Context) {

    private var productService: IProductInterface? = null

    val connection = object: ServiceConnection {
        override fun onServiceConnected(
            p0: ComponentName?,
            service: IBinder?
        ) {
            productService = IProductInterface.Stub.asInterface(service)
        }

        override fun onServiceDisconnected(p0: ComponentName?) {
            productService = null
        }

    }

    fun bind() {
        val intent = Intent("ProductService")
        intent.setPackage("com.example.productserviceconsumer")

        context.bindService(intent, connection, BIND_AUTO_CREATE)
    }

    fun unbind() {
        context.unbindService(connection)
        productService = null
    }

    suspend fun fetchProducts(): List<Product?> = suspendCancellableCoroutine { continuation ->
        val callback = object : IProductCallback.Stub() {

            override fun onSuccess(products: List<Product>) {
                continuation.resume(products)
            }

            override fun onError(message: String) {
                continuation.resumeWithException(RuntimeException(message))
            }
        }

        continuation.invokeOnCancellation {
            runCatching {
                productService?.unregisterCallback(callback)
            }
        }

        productService?.fetchProducts(callback)
    }

}