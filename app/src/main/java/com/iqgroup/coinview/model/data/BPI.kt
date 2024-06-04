package com.iqgroup.coinview.model.data

/**
 * There might be some questions about naming conventions.
 * IMO: abbreviations should not be subjects to camelCase.
 */
@Suppress("PropertyName")
data class BPI(

    val USD: CurrencyEntry,
    val GBP: CurrencyEntry,
    val EUR: CurrencyEntry

)
