package com.iqgroup.coinview.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun ExpandableContainer(title: String,
                        headerBackground: Color = MaterialTheme.colorScheme.secondary,
                        content: @Composable () -> Unit) {

    var expanded by rememberSaveable { mutableStateOf(false) }

    Box(modifier = Modifier
        .background(MaterialTheme.colorScheme.background)
    ) {
        Column {
            ClickableHeader(text = title,
                headerBackground,
                onClickItem = {expanded = !expanded})
            ExpandableContent(isExpanded = expanded, content = content)
        }
    }

}

@Composable
fun ClickableHeader(text: String, background: Color, onClickItem: () -> Unit) {
    Box(
        modifier = Modifier
            .background(background)
            .clickable(
                onClick = onClickItem
            )
            .padding(8.dp)
    ) {
        Text(
            color = MaterialTheme.colorScheme.onSurface,
            text = text,
            fontSize = 17.sp,
            modifier = Modifier.fillMaxWidth()
        )
    }
    HorizontalDivider(thickness = 2.dp)
}

@Composable
fun ExpandableContent(isExpanded: Boolean, content: @Composable () -> Unit) {

    val expandTransition = remember {
        expandVertically(
            expandFrom = Alignment.Top,
            animationSpec = tween(300)
        ) + fadeIn(
            animationSpec = tween(300)
        )
    }

    val collapseTransition = remember {
        shrinkVertically(
            shrinkTowards = Alignment.Top,
            animationSpec = tween(300)
        ) + fadeOut(
            animationSpec = tween(300)
        )
    }

    AnimatedVisibility(
        visible = isExpanded,
        enter = expandTransition,
        exit = collapseTransition
    ) {
        Row {
            Spacer(modifier = Modifier.width(16.dp))
            Box(modifier = Modifier
                .background(MaterialTheme.colorScheme.tertiary)
                .fillMaxWidth(1f)
            ) {
                Column {
                    content()
                }
            }
        }
    }

}