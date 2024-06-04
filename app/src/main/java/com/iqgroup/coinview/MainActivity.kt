package com.iqgroup.coinview

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel


import com.iqgroup.coinview.api.CoinDeskAPI
import com.iqgroup.coinview.model.TextViewModel
import com.iqgroup.coinview.model.data.BitcoinPriceResponse
import com.iqgroup.coinview.ui.theme.CoinViewTestTheme
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.runBlocking

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            CoinViewTestTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Column {
                        Greeting(
                            name = "Android",
                            modifier = Modifier.padding(innerPadding)
                        )
                        MainScreen()
                    }
                }
            }
        }
    }

}

@Preview(showBackground = true)
@Composable
fun MainScreen(viewModel: TextViewModel = viewModel()) {
    val bitcoinPriceResponse by viewModel.bitcoinPriceResponse.collectAsState()

    Button(
        onClick = { viewModel.refresh() }
    ) {
        Text(text = "Refresh")
    }

    bitcoinPriceResponse?.let {
        ResultText(it)
    }

}

@Composable
fun ResultText(priceResponse : BitcoinPriceResponse) {

    Column {
        Text(text = priceResponse.time.updated)
        Text(text = priceResponse.time.updatedISO)
        Text(text = priceResponse.time.updatedUK)

        Text(text = priceResponse.disclaimer)
        Text(text = priceResponse.chartName)

        Text(text = priceResponse.bpi.USD.code)
        Text(text = priceResponse.bpi.USD.description)
        Text(text = priceResponse.bpi.USD.rate)
        Text(text = priceResponse.bpi.USD.symbol)
        Text(text = priceResponse.bpi.USD.rateFloat.toString())

        Text(text = priceResponse.bpi.GBP.code)
        Text(text = priceResponse.bpi.GBP.description)
        Text(text = priceResponse.bpi.GBP.rate)
        Text(text = priceResponse.bpi.GBP.symbol)
        Text(text = priceResponse.bpi.GBP.rateFloat.toString())

        Text(text = priceResponse.bpi.EUR.code)
        Text(text = priceResponse.bpi.EUR.description)
        Text(text = priceResponse.bpi.EUR.rate)
        Text(text = priceResponse.bpi.EUR.symbol)
        Text(text = priceResponse.bpi.EUR.rateFloat.toString())
    }

}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Composable
fun GreetingPreview() {
    CoinViewTestTheme {
        Greeting("Android")
    }
}