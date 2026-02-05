package edu.ucne.jendri_hidalgo_ap2_p1.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable

@Composable
fun NavHost(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = Screen.BorrameList
    ) {
        composable<Screen.BorrameList> {

        }
        composable<Screen.BorrameEdit> { backStackEntry ->
        }
    }
}