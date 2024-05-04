package com.fredy.mysavingsui.ui

import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Scaffold
import androidx.compose.material.rememberScaffoldState
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.fredy.mysavings.ui.navigation.BottomNavGraph
import com.fredy.mysavings.ui.navigation.NavigationRoute
import com.fredy.mysavings.ui.navigation.bottomBarScreens
import com.fredy.mysavings.ui.navigation.drawerScreens
import com.fredy.mysavings.ui.navigation.navigateSingleTopTo
import kotlinx.coroutines.launch

@Composable
fun MainScreen(
    modifier: Modifier = Modifier,
    backgroundColor: Color = MaterialTheme.colorScheme.background,
    contentColor: Color = MaterialTheme.colorScheme.surface,
    onContentColor: Color = MaterialTheme.colorScheme.onSurface,
    rootNavController: NavHostController
) {
    val navController = rememberNavController()
    val scaffoldState = rememberScaffoldState()
    val scope = rememberCoroutineScope()
    val currentBackStack by navController.currentBackStackEntryAsState()
    val currentDestination = currentBackStack?.destination
    val currentScreen = bottomBarScreens.find { it.route == currentDestination?.route } ?: NavigationRoute.Records
    Scaffold(modifier = modifier,
        backgroundColor = backgroundColor,
        scaffoldState = scaffoldState,
        topBar = {
            AppBar(backgroundColor = contentColor,
                contentColor = onContentColor,
                onNavigationIconClick = {
                    scope.launch {
                        scaffoldState.drawerState.open()
                    }
                })
        },
        drawerGesturesEnabled = scaffoldState.drawerState.isOpen,
        drawerBackgroundColor = contentColor,
        drawerContentColor = onContentColor,
        drawerContent = {
            DrawerHeader()
            DrawerBody(items = drawerScreens,
                onItemClick = { newScreen ->
                    rootNavController.navigateSingleTopTo(
                        newScreen.route
                    )
                })
        },
        bottomBar = {
            BottomBar(
                modifier = Modifier.height(
                    65.dp
                ),
                backgroundColor = contentColor,
                contentColor = onContentColor,
                allScreens = bottomBarScreens,
                onTabSelected = { newScreen ->
                    navController.navigateSingleTopTo(
                        newScreen.route
                    )
                },
                currentScreen = currentScreen
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    rootNavController.navigateSingleTopTo(
                        NavigationRoute.Add.route
                    )
                },
                backgroundColor = contentColor,
            ) {
                Icon(
                    NavigationRoute.Add.icon,
                    modifier = Modifier.size(30.dp),
                    tint = onContentColor,
                    contentDescription = null
                )
            }
        }

    ) { innerPadding ->
        BottomNavGraph(
            navController = navController,
            modifier = Modifier.padding(
                innerPadding
            ),
        )
    }
}