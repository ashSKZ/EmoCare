package com.example.emocare.ui

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.*
import androidx.navigation.navArgument
import com.example.emocare.ui.navigation.NavRoutes
import com.example.emocare.ui.screens.*
import com.example.emocare.ui.screens.ExploreExercisesScreen


@Composable
fun MainScreen() {
    val navController = rememberNavController()

    Scaffold(
        bottomBar = {
            BottomBar(navController = navController)
        }
    ) { padding ->
        NavHost(
            navController = navController,
            startDestination = NavRoutes.Home.route,
            modifier = Modifier.padding(padding)
        ) {
            composable(NavRoutes.Home.route) {
                HomeScreen(navController = navController)
            }
            composable(NavRoutes.History.route) {
                HistoryScreen()
            }
            composable(NavRoutes.Profile.route) {
                ProfileScreen()
            }
            composable(NavRoutes.Relaxation.route) {
                RelaxationScreen(navController)
            }

            composable(
                route = NavRoutes.Result.route,
                arguments = listOf(
                    navArgument("emotion") { type = NavType.StringType },
                    navArgument("confidence") { type = NavType.StringType },
                    navArgument("recommendation") { type = NavType.StringType }
                )
            ) { backStackEntry ->
                ResultScreen(
                    entry = backStackEntry,
                    onBack = {
                        navController.popBackStack(NavRoutes.Home.route, inclusive = false)
                    }
                )
            }
            composable(NavRoutes.ExploreExercises.route) {
                ExploreExercisesScreen(navController)
            }



        }
    }
}

@Composable
fun BottomBar(navController: NavHostController) {
    val items = listOf(
        BottomNavItem("Detectar", Icons.Default.Home, NavRoutes.Home),
        BottomNavItem("Historial", Icons.Default.History, NavRoutes.History),
        BottomNavItem("Perfil", Icons.Default.Person, NavRoutes.Profile),
        BottomNavItem("Relajación", Icons.Default.SelfImprovement, NavRoutes.Relaxation) // ✅ nuevo ítem
    )

    val currentBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = currentBackStackEntry?.destination?.route

    NavigationBar {
        items.forEach { item ->
            NavigationBarItem(
                selected = currentRoute?.startsWith(item.route.route) == true,
                onClick = {
                    navController.navigate(item.route.route) {
                        popUpTo(NavRoutes.Home.route) { inclusive = false }
                        launchSingleTop = true
                    }
                },
                icon = { Icon(item.icon, contentDescription = item.label) },
                label = { Text(item.label) }
            )
        }
    }
}

data class BottomNavItem(val label: String, val icon: ImageVector, val route: NavRoutes)
