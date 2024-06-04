package com.iqgroup.coinview.model.data

data class BitcoinPriceResponse(

    val time: UpdateTime,
    val disclaimer: String,
    val chartName: String,
    val bpi: BPI

)
