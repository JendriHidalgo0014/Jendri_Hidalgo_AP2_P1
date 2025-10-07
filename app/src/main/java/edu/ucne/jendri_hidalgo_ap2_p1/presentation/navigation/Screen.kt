package edu.ucne.jendri_hidalgo_ap2_p1.presentation.navigation

sealed class Screen(val route: String) {
    object HuacalesList : Screen("huacales_list")

    object EditHuacales : Screen("edit_huacales") {
        // Función para crear la ruta con parámetro
        fun createRoute(entradaId: Int?): String {
            return if (entradaId != null) "edit_huacales/$entradaId" else "edit_huacales"
        }
    }

    companion object {
        // Constantes para los argumentos
        const val ENTRADA_ID_ARG = "entradaId"
    }
}