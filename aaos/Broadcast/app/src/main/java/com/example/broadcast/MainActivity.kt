package com.example.broadcast

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import androidx.core.content.ContextCompat
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.broadcast.ui.theme.BroadcastTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()
        setContent {
            BroadcastTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    TripScreen(context = this, modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}

@Composable
fun TripScreen(context: Context, modifier: Modifier = Modifier) {
    val summaries = remember { mutableStateListOf<TripSummary>() }

    DisposableEffect(Unit) {
        val receiver = object : BroadcastReceiver() {
            override fun onReceive(ctx: Context, intent: Intent) {
                val tripId = intent.getStringExtra(TripContract.EXTRA_TRIP_ID) ?: return
                val count = intent.getIntExtra(TripContract.EXTRA_WAYPOINT_COUNT, 0)
                summaries.add(0, TripSummary(tripId, count)) // newest first
            }
        }
        val filter = IntentFilter(TripContract.ACTION_TRIP_SUMMARY_READY)

        ContextCompat.registerReceiver(
            context, receiver, filter, ContextCompat.RECEIVER_NOT_EXPORTED
        )

        onDispose { context.unregisterReceiver(receiver) }
    }

    Column(modifier = modifier.fillMaxSize().padding(16.dp)) {
        Button(onClick = {
            sendTripEndedBroadcast(context, "trip-1")
        }) {
            Text("End trip-1")
        }

        Spacer(Modifier.height(8.dp))

        Button(onClick = {
            sendTripEndedBroadcast(context, "trip-2")
        }) {
            Text("End trip-2")
        }

        Spacer(Modifier.height(16.dp))

        Text("Summaries (async, arrive ~1s after button press):", fontWeight = FontWeight.Bold)

        LazyColumn {
            items(summaries) { summary ->
                Text("${summary.tripId}: ${summary.waypointCount} waypoints")
            }
        }
    }
}

fun sendTripEndedBroadcast(context: Context, tripId: String) {
    val intent = Intent(TripContract.ACTION_TRIP_ENDED).apply {
        setPackage(context.packageName)
        putExtra(TripContract.EXTRA_TRIP_ID, tripId)
    }

    context.sendBroadcast(intent, TripContract.PERMISSION_RECEIVE_TRIP_EVENTS)
}

data class TripSummary(val tripId: String, val waypointCount: Int)
