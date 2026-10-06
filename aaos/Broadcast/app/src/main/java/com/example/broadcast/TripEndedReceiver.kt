package com.example.broadcast

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class TripEndedReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        if (intent.action != TripContract.ACTION_TRIP_ENDED) {
            return
        }

        val tripId = intent.getStringExtra(TripContract.EXTRA_TRIP_ID) ?: return

        val pendingResult = goAsync()

        CoroutineScope(Dispatchers.Default).launch {
            try {
                delay(1_000)

                val count = TripWaypointStore.waypointCount(tripId)

                val summaryIntent = Intent(TripContract.ACTION_TRIP_SUMMARY_READY).apply {
                    setPackage(context.packageName)
                    putExtra(TripContract.EXTRA_TRIP_ID, tripId)
                    putExtra(TripContract.EXTRA_WAYPOINT_COUNT, count)
                }

                context.sendBroadcast(summaryIntent)
            } finally {
                pendingResult.finish()
            }
        }
    }
}