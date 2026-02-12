package edu.ucne.jendri_hidalgo_ap2_p1.presentation.tareas.list

import edu.ucne.jendri_hidalgo_ap2_p1.domain.model.Cervezas
data class ListCervezaUiState(
    val isLoading: Boolean = false,
    val cervezas: List<Cervezas> = emptyList(),
    val cervezasFiltradas: List<Cervezas> = emptyList(),
    val filtroNombre: String = "",
    val filtroMarca: String = "",
    val totalCervezas: Int = 0,
    val promedioPuntuacion: Double = 0.0,
    val message: String? = null,
    val navigateToCreate: Boolean = false,
    val navigateToEditId: Int? = null
)