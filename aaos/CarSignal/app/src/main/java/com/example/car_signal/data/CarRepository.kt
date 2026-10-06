package com.example.car_signal.data

import android.car.Car
import android.car.VehiclePropertyIds
import android.car.hardware.CarPropertyValue
import android.car.hardware.property.CarPropertyManager
import android.car.hardware.property.Subscription
import android.content.Context
import android.content.pm.PackageManager
import androidx.core.content.ContextCompat
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.sample

class CarRepository(private val context: Context) {

    private var car: Car? = null
    private var propertyManager: CarPropertyManager? = null

    private var serviceConnection = object : Car.CarServiceLifecycleListener {
        override fun onLifecycleChanged(car: Car?, ready: Boolean) {
            propertyManager = if (ready) {
                car?.getCarManager(CarPropertyManager::class.java)
            } else {
                null
            }
        }
    }

    init {
        car = Car.createCar(
            context,
            null,
            Car.CAR_WAIT_TIMEOUT_WAIT_FOREVER,
            serviceConnection
        )
    }

    @OptIn(FlowPreview::class)
    fun speedFlow(): Flow<Float> = carProperty(
        VehiclePropertyIds.PERF_VEHICLE_SPEED,
        Car.PERMISSION_SPEED,
        { it?.value as Float }
    )
    .filterNotNull()
    .sample(300)

    fun gearFlow(): Flow<Int> = carProperty(
        VehiclePropertyIds.GEAR_SELECTION,
        Car.PERMISSION_POWERTRAIN,
        { it?.value as Int }
    )
    .filterNotNull()

    private fun <T: Any> carProperty(
        propertyId: Int,
        permission: String,
        map: (CarPropertyValue<*>?) -> T
    ): Flow<T?> = callbackFlow {
        if (context.checkSelfPermission(permission) != PackageManager.PERMISSION_GRANTED) {
            close(SecurityException("Permission denied: $permission"))
            return@callbackFlow
        }

        val callback = object: CarPropertyManager.CarPropertyEventCallback {
            override fun onChangeEvent(value: CarPropertyValue<*>?) {
                trySend(map(value))
            }

            override fun onErrorEvent(p0: Int, p1: Int) {
                close(IllegalStateException("VHAL erorr property ${propertyId}"))
            }
        }

        propertyManager?.subscribePropertyEvents(propertyId, callback)

        awaitClose { propertyManager?.unsubscribePropertyEvents(callback) }
    }

}