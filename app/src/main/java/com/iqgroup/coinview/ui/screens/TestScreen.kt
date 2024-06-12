package com.iqgroup.coinview.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.iqgroup.coinview.navigation.Screens
import com.iqgroup.coinview.ui.components.CenteredBox

@Composable
fun TestScreen(
    navController: NavController
) {
    CenteredBox {
        Column {
            Text("Test Screen")
            Button(onClick = { navController.navigate(Screens.PriceScreen.route) }) {
                Text(text = "To price screen")
            }
        }

    }
}