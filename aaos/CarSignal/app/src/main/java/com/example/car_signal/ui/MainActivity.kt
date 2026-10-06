package com.example.car_signal.ui

import android.car.Car
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.car_signal.data.CarRepository
import com.example.car_signal.ui.car.CarUiState
import com.example.car_signal.ui.car.CarViewModel
import com.example.car_signal.ui.theme.CarsignalTheme

class MainActivity : ComponentActivity() {

    val viewModel: CarViewModel by viewModels {
        viewModelFactory {
            initializer {
                CarViewModel(
                    CarRepository(applicationContext)
                )
            }
        }
    }

    private val requestSpeedPermission =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { granted ->
            viewModel.onPermissionChanged(granted)
        }

    override fun onStart() {
        super.onStart()

        val granted = checkSelfPermission(Car.PERMISSION_SPEED) == PackageManager.PERMISSION_GRANTED
        viewModel.onPermissionChanged(granted)

        if (!granted) {
            requestSpeedPermission.launch(Car.PERMISSION_SPEED)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val uiState = viewModel.uiState.collectAsStateWithLifecycle()

            CarsignalTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Column {
                        Greeting(
                            name = "Android",
                            modifier = Modifier.padding(innerPadding)
                        )

                        when (uiState.value) {
                            is CarUiState.Loading -> {
                                Text(text = "Loading...")
                            }

                            is CarUiState.Success -> {
                                val successState = uiState.value as CarUiState.Success
                                Text(text = "Speed: ${successState.speed}")
                                Text(text = "Gear: ${successState.gear}")
                            }

                            is CarUiState.Error -> {
                                val errorState = uiState.value as CarUiState.Error
                                Text(text = errorState.message)
                            }

                            is CarUiState.PermissionRequired -> {
                                Text(text = "Permission required")
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    CarsignalTheme {
        Greeting("Android")
    }
}