package edu.ucne.jendri_hidalgo_ap2_p1.presentation.navigation

import androidx.compose.foundation.gestures.ScrollableState
import androidx.compose.runtime.Composable
import androidx.compose.ui.window.Popup
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navigation
import edu.ucne.jendri_hidalgo_ap2_p1.presentation.tareas.edit.EditHuacalesScreen
import edu.ucne.jendri_hidalgo_ap2_p1.presentation.tareas.list.ListHuacalesScreen

@Composable
fun Jendri_Hidalgo_AP2_P1NavHost(
    navController: NavHostController // Cambiado a navController (más estándar)
) {
    NavHost(
        navController = navController,
        startDestination = Screen.HuacalesList.route // Usar la ruta definida en Screen
    ) {
        composable(Screen.HuacalesList.route) {
            ListHuacalesScreen(
                onNavigateToCreate = {id->

                    navController.navigate(Screen.EditHuacales.createRoute(id))
                },
                onNavigateToEdit = { id ->

                    navController.navigate(Screen.EditHuacales.createRoute(id))
                },

            )
        }

        composable(
            route = "edit_huacales/{${Screen.ENTRADA_ID_ARG}}?",
            arguments = listOf(
                navArgument(Screen.ENTRADA_ID_ARG) {
                    type = NavType.StringType // Cambiamos a StringType
                    nullable = true
                    defaultValue = null
                }
            )
        ) { backStackEntry ->
            val entradaIdString = backStackEntry.arguments?.getString(Screen.ENTRADA_ID_ARG)
            val entradaId = entradaIdString?.toIntOrNull() // Convertimos a Int? (puede ser null)
            EditHuacalesScreen(
                entradaId = entradaId?: 0,
                onSaveSuccess = {
                    navController.popBackStack()
                },
                goBack = {
                    navController.popBackStack()
                }
            )
        }
    }
}