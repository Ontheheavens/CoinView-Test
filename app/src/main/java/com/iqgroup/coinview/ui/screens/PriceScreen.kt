package com.iqgroup.coinview.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
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
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.lifecycle.viewmodel.compose.viewModel
import com.iqgroup.coinview.api.NetworkResult
import com.iqgroup.coinview.model.ResponseViewModel
import com.iqgroup.coinview.model.data.BitcoinPriceResponse
import com.iqgroup.coinview.navigation.DefaultNavArgs
import com.iqgroup.coinview.ui.components.BitcoinPriceView
import com.iqgroup.coinview.ui.components.CenteredBox
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootGraph
import com.ramcosta.composedestinations.generated.destinations.TestScreenDestination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@Destination<RootGraph>(
    navArgs = DefaultNavArgs::class,
    start = true
)
@Composable
fun PriceScreen(
    viewModel: ResponseViewModel = viewModel(),
    navigator: DestinationsNavigator
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
            contentAlignment = Alignment.CenterStart,
            modifier = Modifier
                .background(color = MaterialTheme.colorScheme.surfaceContainer)
                .fillMaxWidth()
                .height(56.dp)
                .constrainAs(topBar) {
                    top.linkTo(statusSpacer.bottom)
                    start.linkTo(parent.start)
                }
        ) {
            Row {
                Text(
                    color = MaterialTheme.colorScheme.onSurface,
                    text = "CoinView",
                    textAlign = TextAlign.Center,
                    fontSize = 24.sp,
                    modifier = Modifier.padding(
                        PaddingValues(16.dp, 4.dp, 4.dp, 4.dp)
                    )
                )
                Spacer(modifier = Modifier.weight(1f))
                Button(onClick = {
                    navigator.navigate(TestScreenDestination.invoke())
                }) {
                    Text(text = "To test screen")
                }
            }

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
            text = {
                Text(
                    color = MaterialTheme.colorScheme.onSurface,
                    text = "Refresh"
                )
            },
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