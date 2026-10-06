package com.example.broadcast

object TripWaypointStore {

    val waypointsByTrip = mutableMapOf(
        "trip-1" to listOf("Home", "Office", "Gym"),
        "trip-2" to listOf("Office", "Grocery Store")
    )

    fun waypointCount(tripId: String): Int = waypointsByTrip[tripId]?.size ?: 0

}