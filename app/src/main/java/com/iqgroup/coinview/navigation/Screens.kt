package com.iqgroup.coinview.navigation

sealed class Screens(val route: String) {
    data object PriceScreen: Screens("price_screen")
    data object TestScreen: Screens("test_screen")

}

