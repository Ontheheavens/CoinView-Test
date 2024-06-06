package com.iqgroup.coinview.model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.iqgroup.coinview.api.CoinDeskAPI
import com.iqgroup.coinview.api.NetworkResult
import com.iqgroup.coinview.model.data.BitcoinPriceResponse
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

/**
 * A view model that retrieves the current Bitcoin price from the CoinDesk API and exposes it as a state flow.
 *
 * The [ResponseViewModel] class is responsible for fetching the current Bitcoin price from the CoinDesk API and
 * making it available to the UI layer through a [StateFlow]. The class initializes the state flow with a
 * null value and then updates it with the latest price when the [refresh] function is called.
 */
class ResponseViewModel : ViewModel() {

    private val response = MutableStateFlow<NetworkResult<BitcoinPriceResponse>?>(NetworkResult.Loading)
    val bitcoinPriceResponse: StateFlow<NetworkResult<BitcoinPriceResponse>?> = response

    init {
        refresh()
    }

    fun refresh() {
        viewModelScope.launch {
            response.value = fetchBitcoinPrice()
        }
    }

    private suspend fun fetchBitcoinPrice(): NetworkResult<BitcoinPriceResponse> {
        return try {
            val response = CoinDeskAPI.api.getCurrentPrice()
            NetworkResult.Success(response)
        } catch (e: IOException) {
            NetworkResult.Error("Network error. Please check your connection.")
        } catch (e: HttpException) {
            val errorMessage = when (e.code()) {
                404 -> "Resource not found"
                500 -> "Server error. Please try again later."
                else -> "Unexpected error"
            }
            NetworkResult.Error(errorMessage)
        } catch (e: Exception) {
            NetworkResult.Error("An unknown error occurred.")
        }
    }

}