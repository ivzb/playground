package com.example.productserviceconsumer

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.productserviceconsumer.ui.theme.ProductServiceConsumerTheme

class MainActivity : ComponentActivity() {

    val productServiceConnector = ProductServiceConnector(this)

    val userViewModel: UserViewModel by viewModels {
        viewModelFactory {
            initializer {
                UserViewModel(
                    ProductRepository(productServiceConnector)
                )
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        productServiceConnector.bind()

        setContent {
            val uiState = userViewModel.uiState.collectAsStateWithLifecycle()

            ProductServiceConsumerTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Column (modifier = Modifier
                        .padding(innerPadding)
                        .fillMaxSize()
                    ) {

                        Text(
                            modifier = Modifier.padding(8.dp),
                            text = "Hello there!",
                        )

                        if (uiState.value !is UserUiState.Loading) {
                            Button(
                                modifier = Modifier
                                    .align(Alignment.CenterHorizontally),
                                onClick = {
                                    userViewModel.fetchProducts()
                                }
                            ) {
                                Text("Fetch products")
                            }
                        }

                        Spacer(modifier = Modifier.padding(8.dp))

                        when (uiState.value) {
                            is UserUiState.Loading -> {
                                CircularProgressIndicator(modifier = Modifier
                                    .size(48.dp)
                                    .align(Alignment.CenterHorizontally)
                                )
                            }

                            is UserUiState.Success -> {
                                val products = (uiState.value as UserUiState.Success).products
                                LazyColumn {
                                    items(products.size) { index ->
                                        val product = products[index]
                                        Text("Product #${product?.id}: ${product?.name}")
                                    }
                                }
                            }

                            is UserUiState.Error -> {
                                Text("Error: ${(uiState.value as UserUiState.Error).message}")
                            }
                        }
                    }
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        productServiceConnector.unbind()
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    ProductServiceConsumerTheme {
        Greeting("Android")
    }
}