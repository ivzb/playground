package com.example.broadcast

object TripContract {

    const val ACTION_TRIP_ENDED = "com.example.broadcast.ACTION_TRIP_ENDED"
    const val ACTION_TRIP_SUMMARY_READY = "com.example.broadcast.ACTION_TRIP_SUMMARY_READY"

    const val EXTRA_TRIP_ID = "trip_id"
    const val EXTRA_WAYPOINT_COUNT = "waypoint_count"

    const val PERMISSION_RECEIVE_TRIP_EVENTS = "com.example.broadcast.permission.RECEIVE_TRIP_EVENTS"

}