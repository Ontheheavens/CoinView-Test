package com.iqgroup.coinview.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import com.ramcosta.composedestinations.DestinationsNavHost
import com.ramcosta.composedestinations.generated.NavGraphs


@Composable
fun AppNavigation (navController: NavHostController){
    DestinationsNavHost(
        navGraph = NavGraphs.root,
        navController = navController,
    ) {
    }
}

data class DefaultNavArgs(
    val id: Int = -1,
    val groupName: String? = null
)