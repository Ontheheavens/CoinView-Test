package com.iqgroup.coinview


import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.windowInsetsTopHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.lifecycle.viewmodel.compose.viewModel
import com.iqgroup.coinview.model.TextViewModel
import com.iqgroup.coinview.model.data.BitcoinPriceResponse
import com.iqgroup.coinview.ui.components.ExpandableContainer
import com.iqgroup.coinview.ui.theme.CoinViewTestTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            CoinViewTestTheme {
                ConstraintLayout {
                    MainScreen()
                }
            }
        }
    }

}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    viewModel: TextViewModel = viewModel()
) {

    val bitcoinPriceResponse by viewModel.bitcoinPriceResponse.collectAsState()

    ConstraintLayout(
        modifier = Modifier.fillMaxSize()
    ) {

        val (statusSpacer,
            topBar,
            mainArea,
            refreshButton) = createRefs()

        Spacer(
            Modifier
                .windowInsetsTopHeight(
                    WindowInsets.statusBars
                )
                .height(IntrinsicSize.Min)
                .constrainAs(statusSpacer) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                }
        )

        TopAppBar(
            title = { Text(
                text = "CoinView",
                textAlign = TextAlign.Center
            )},
            modifier = Modifier
                .background(color = MaterialTheme.colorScheme.surfaceContainer)
                .fillMaxWidth()
                .height(56.dp)
                .constrainAs(topBar) {
                    top.linkTo(statusSpacer.bottom)
                    start.linkTo(parent.start)
                }
        )

        Column(
            modifier = Modifier
                .verticalScroll(rememberScrollState())
                .fillMaxHeight()
                .background(MaterialTheme.colorScheme.secondary)
                .constrainAs(mainArea) {
                    top.linkTo(topBar.bottom)
                    start.linkTo(parent.start)
                }
        ) {
            bitcoinPriceResponse?.let {
                val priceResponse = it
                ExpandableContainer(title = "Time") {
                    Column {
                        Text(text = "Updated: ${priceResponse.time.updated}")
                        Text(text = "Updated ISO: ${priceResponse.time.updatedISO}")
                        Text(text = "Updated UK: ${priceResponse.time.updatedUK}")
                    }
                }

                ExpandableContainer(title = "Disclaimer") {
                    Text(text = priceResponse.disclaimer)
                }

                ExpandableContainer(title = "Chart Name") {
                    Text(text = priceResponse.chartName)
                }

                ExpandableContainer(title = "BPI") {
                    Column {
                        ExpandableContainer(title = "USD") {
                            Column {
                                Text(text = "Code: ${priceResponse.bpi.USD.code}")
                                Text(text = "Description: ${priceResponse.bpi.USD.description}")
                                Text(text = "Rate: ${priceResponse.bpi.USD.rate}")
                                Text(text = "Symbol: ${priceResponse.bpi.USD.symbol}")
                                Text(text = "Rate Float: ${priceResponse.bpi.USD.rateFloat}")
                            }
                        }

                        ExpandableContainer(title = "GBP") {
                            Column {
                                Text(text = "Code: ${priceResponse.bpi.GBP.code}")
                                Text(text = "Description: ${priceResponse.bpi.GBP.description}")
                                Text(text = "Rate: ${priceResponse.bpi.GBP.rate}")
                                Text(text = "Symbol: ${priceResponse.bpi.GBP.symbol}")
                                Text(text = "Rate Float: ${priceResponse.bpi.GBP.rateFloat}")
                            }
                        }

                        ExpandableContainer(title = "EUR") {
                            Column {
                                Text(text = "Code: ${priceResponse.bpi.EUR.code}")
                                Text(text = "Description: ${priceResponse.bpi.EUR.description}")
                                Text(text = "Rate: ${priceResponse.bpi.EUR.rate}")
                                Text(text = "Symbol: ${priceResponse.bpi.EUR.symbol}")
                                Text(text = "Rate Float: ${priceResponse.bpi.EUR.rateFloat}")
                            }
                        }
                    }
                }
            }
        }

        ExtendedFloatingActionButton(
            modifier = Modifier
                .constrainAs(refreshButton) {
                    bottom.linkTo(parent.bottom, margin = 16.dp)
                    end.linkTo(parent.end, margin = 16.dp)
                }
                .padding(16.dp),
            onClick = { viewModel.refresh() },
            icon = { Icon(Icons.Filled.Refresh, "Refresh button.") },
            text = { Text(text = "Refresh") },
        )

    }

}