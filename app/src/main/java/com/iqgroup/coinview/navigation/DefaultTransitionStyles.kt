package com.iqgroup.coinview.navigation

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.navigation.NavBackStackEntry
import com.ramcosta.composedestinations.spec.DestinationStyle

object DefaultTransitionStyles : DestinationStyle.Animated() {

    override val enterTransition: AnimatedContentTransitionScope<NavBackStackEntry>.() ->
    EnterTransition? = {
        fadeIn(animationSpec = tween(700))
    }

    override val exitTransition: AnimatedContentTransitionScope<NavBackStackEntry>.() ->
    ExitTransition? = {
        fadeOut(animationSpec = tween(700))
    }

    override val popEnterTransition: AnimatedContentTransitionScope<NavBackStackEntry>.() ->
    EnterTransition? = {
        fadeIn(animationSpec = tween(700))
    }

    override val popExitTransition: AnimatedContentTransitionScope<NavBackStackEntry>.() ->
    ExitTransition? = {
        fadeOut(animationSpec = tween(700))
    }

}