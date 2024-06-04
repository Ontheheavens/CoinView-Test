package com.iqgroup.coinview.model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.iqgroup.coinview.api.CoinDeskAPI
import com.iqgroup.coinview.model.data.BitcoinPriceResponse
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

/**
 * A view model that retrieves the current Bitcoin price from the CoinDesk API and exposes it as a state flow.
 *
 * The [TextViewModel] class is responsible for fetching the current Bitcoin price from the CoinDesk API and
 * making it available to the UI layer through a [StateFlow]. The class initializes the state flow with a
 * null value and then updates it with the latest price when the [refresh] function is called.
 */
class TextViewModel : ViewModel() {

    private val response = MutableStateFlow<BitcoinPriceResponse?>(null)
    val bitcoinPriceResponse: StateFlow<BitcoinPriceResponse?> = response

    init {
        refresh()
    }

    fun refresh() {
        viewModelScope.launch {
            response.value = CoinDeskAPI.api.getCurrentPrice()
        }
    }

}