package edu.ucne.jendri_hidalgo_ap2_p1.presentation.navigation


import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import edu.ucne.jendri_hidalgo_ap2_p1.presentation.tareas.edit.EditCervezaScreen
import edu.ucne.jendri_hidalgo_ap2_p1.presentation.tareas.list.ListCervezaScreen

@Composable
fun NavHost(
    navHostController: NavHostController
) {
    NavHost(
        navController = navHostController,
        startDestination = Screen.CervezaList
    ) {
        composable<Screen.CervezaList> {
            ListCervezaScreen (
                onDrawer = { },
                onCreate = {
                    navHostController.navigate(Screen.CervezaEdit(0))
                },
                onEdit = { cervezaId ->
                    navHostController.navigate(Screen.CervezaEdit(cervezaId))
                }
            )
        }

    }
}

