package com.iqgroup.coinview.model.data

import com.fasterxml.jackson.annotation.JsonProperty

data class CurrencyEntry(
    val code: String,
    val symbol: String,
    val rate: String,
    val description: String,

    @JsonProperty("rate_float")
    val rateFloat: Float
)