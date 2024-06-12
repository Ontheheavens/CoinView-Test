package com.iqgroup.coinview.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.iqgroup.coinview.ui.screens.PriceScreen
import com.iqgroup.coinview.ui.screens.TestScreen


@Composable
fun NavGraph (navController: NavHostController){
    NavHost(
        navController = navController,
        startDestination = Screens.PriceScreen.route)
    {
        composable(route = Screens.PriceScreen.route){
            PriceScreen(navController = navController)
        }
        composable(route = Screens.TestScreen.route){
            TestScreen(navController = navController)
        }
    }
}