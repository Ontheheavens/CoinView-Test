package com.iqgroup.coinview.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.iqgroup.coinview.navigation.DefaultNavArgs
import com.iqgroup.coinview.navigation.DefaultTransitionStyles
import com.iqgroup.coinview.ui.components.CenteredBox
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootGraph
import com.ramcosta.composedestinations.generated.destinations.PriceScreenDestination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.ramcosta.composedestinations.spec.Direction

@Destination<RootGraph>(
    navArgs = DefaultNavArgs::class,
    style = DefaultTransitionStyles::class
)
@Composable
fun TestScreen(
    navigator: DestinationsNavigator
) {
    CenteredBox {
        Column {
            Text("Test Screen")
            Button(onClick = {
                navigator.navigate(PriceScreenDestination.invoke())
            }) {
                Text(text = "To price screen")
            }
        }

    }
}