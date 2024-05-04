package com.fredy.mysavings.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.fredy.mysavingsui.ui.MainScreen

@Composable
fun NavGraphRoot(
    navController: NavHostController,
) {
    NavHost(
        navController = navController,
        startDestination = Graph.MainNav,
        route = Graph.Root
    ) {
        authenticationNavGraph(navController = navController)
        navigation(
            route = Graph.MainNav,
            startDestination = Graph.BottomNav
        ) {
            composable(
                route = Graph.BottomNav
            ) {
                MainScreen(
                    rootNavController = navController
                )
            }
        }
        composable(
            route = NavigationRoute.Add.route
        ) {
//            AddScreen()
        }
    }
}

fun NavHostController.navigateSingleTopTo(route: String) = this.navigate(
    route
) { launchSingleTop = true }