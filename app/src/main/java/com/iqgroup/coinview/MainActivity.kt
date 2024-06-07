package com.iqgroup.coinview

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.windowInsetsBottomHeight
import androidx.compose.foundation.layout.windowInsetsTopHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Alignment.Companion.CenterStart
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.lifecycle.viewmodel.compose.viewModel
import com.iqgroup.coinview.api.NetworkResult
import com.iqgroup.coinview.model.ResponseViewModel
import com.iqgroup.coinview.model.data.BitcoinPriceResponse
import com.iqgroup.coinview.model.data.CurrencyEntry
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

@Composable
fun MainScreen(
    viewModel: ResponseViewModel = viewModel()
) {

    val bitcoinPriceResponse by viewModel.bitcoinPriceResponse.collectAsState()

    ConstraintLayout(
        modifier = Modifier.fillMaxSize()
    ) {

        val (statusSpacer,
            topBar,
            mainArea,
            refreshButton,
            systemSpacer) = createRefs()

        Spacer(
            Modifier
                .background(MaterialTheme.colorScheme.background)
                .windowInsetsTopHeight(
                    WindowInsets.statusBars
                )
                .fillMaxWidth()
                .height(IntrinsicSize.Min)
                .constrainAs(statusSpacer) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                }
        )

        Box(
            contentAlignment = CenterStart,
            modifier = Modifier
                .background(color = MaterialTheme.colorScheme.surfaceContainer)
                .fillMaxWidth()
                .height(56.dp)
                .constrainAs(topBar) {
                    top.linkTo(statusSpacer.bottom)
                    start.linkTo(parent.start)
                }
        ) {
            Text(
                color = MaterialTheme.colorScheme.onSurface,
                text = "CoinView",
                textAlign = TextAlign.Center,
                fontSize = 24.sp,
                modifier = Modifier.padding(
                    PaddingValues(16.dp, 4.dp, 4.dp, 4.dp)
                )
            )
        }

        when (bitcoinPriceResponse) {
            is NetworkResult.Loading -> {
                CenteredBox {
                    CircularProgressIndicator()
                }
            }
            is NetworkResult.Success -> {
                val priceResponse = (
                        bitcoinPriceResponse as NetworkResult.Success<BitcoinPriceResponse>
                        ).data

                val scrollState = rememberScrollState()

                Column(
                    modifier = Modifier
                        .verticalScroll(state = scrollState)
                        .background(MaterialTheme.colorScheme.background)
                        .constrainAs(mainArea) {
                            top.linkTo(topBar.bottom)
                            start.linkTo(parent.start)
                            bottom.linkTo(systemSpacer.top)
                            end.linkTo(parent.end)
                            height = Dimension.fillToConstraints
                        }
                ) {
                    BitcoinPriceView(priceResponse)
                }
            }
            is NetworkResult.Error -> {
                val errorMessage = (bitcoinPriceResponse as NetworkResult.Error).message
                CenteredBox {
                    Text(
                        color = MaterialTheme.colorScheme.onSurface,
                        text = errorMessage
                    )
                }
            }
            null -> {
                CenteredBox {
                    Text(
                        color = MaterialTheme.colorScheme.onSurface,
                        text = "An unknown error occurred."
                    )
                }
            }
        }

        ExtendedFloatingActionButton(
            modifier = Modifier
                .constrainAs(refreshButton) {
                    bottom.linkTo(systemSpacer.top, margin = 2.dp)
                    end.linkTo(parent.end, margin = 2.dp)
                }
                .padding(16.dp),
            onClick = { viewModel.refresh() },
            icon = { Icon(Icons.Filled.Refresh, "Refresh button.") },
            text = { Text(
                color = MaterialTheme.colorScheme.onSurface,
                text = "Refresh"
            ) },
            contentColor = MaterialTheme.colorScheme.onSurface,
        )

        Spacer(
            Modifier
                .background(MaterialTheme.colorScheme.background)
                .windowInsetsBottomHeight(
                    WindowInsets.systemBars
                )
                .height(IntrinsicSize.Min)
                .constrainAs(systemSpacer) {
                    bottom.linkTo(parent.bottom)
                    start.linkTo(parent.start)
                }
        )

    }

}

@Composable
fun CenteredBox(content: @Composable () -> Unit) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Center
    ) {
        content()
    }
}

@Composable
fun BitcoinPriceView(priceResponse: BitcoinPriceResponse) {

    ExpandableContainer(title = "Time") {
        Column {
            DividedTextEntry(textContent = "Updated: ${priceResponse.time.updated}")
            DividedTextEntry(textContent = "Updated ISO: ${priceResponse.time.updatedISO}")
            DividedTextEntry(textContent = "Updated UK: ${priceResponse.time.updatedUK}")
        }
    }

    ExpandableContainer(title = "Disclaimer") {
        DividedTextEntry(textContent = priceResponse.disclaimer)
    }

    ExpandableContainer(title = "Chart Name") {
        DividedTextEntry(textContent = priceResponse.chartName)
    }

    ExpandableContainer(title = "BPI") {
        Column {
            CurrencyDataView(title = "USD", priceResponse.bpi.USD)
            CurrencyDataView(title = "GBP", priceResponse.bpi.GBP)
            CurrencyDataView(title = "EUR", priceResponse.bpi.EUR)
        }
    }

    Spacer(modifier = Modifier.height(8.dp))
}

@Composable
fun CurrencyDataView(title: String, currencyEntry: CurrencyEntry) {
    ExpandableContainer(title = title, MaterialTheme.colorScheme.secondaryContainer) {
        Column {
            DividedTextEntry(textContent = "Code: ${currencyEntry.code}")
            DividedTextEntry(textContent = "Description: ${currencyEntry.description}")
            DividedTextEntry(textContent = "Rate: ${currencyEntry.rate}")
            DividedTextEntry(textContent = "Symbol: ${currencyEntry.symbol}")
            DividedTextEntry(textContent = "Rate Float: ${currencyEntry.rateFloat}")
        }
    }
}

@Composable
fun DividedTextEntry(textContent: String) {
    Text(
        color = MaterialTheme.colorScheme.onSurface,
        text = textContent,
        modifier = Modifier.padding(4.dp)
    )
    HorizontalDivider(thickness = 2.dp)
}