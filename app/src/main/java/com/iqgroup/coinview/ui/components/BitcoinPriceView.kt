package com.iqgroup.coinview.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.iqgroup.coinview.model.data.BitcoinPriceResponse
import com.iqgroup.coinview.model.data.CurrencyEntry

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