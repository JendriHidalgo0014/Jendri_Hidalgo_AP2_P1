package edu.ucne.jendri_hidalgo_ap2_p1.presentation.tareas.list

import edu.ucne.jendri_hidalgo_ap2_p1.domain.model.EntradaHucales

data class ListHuacalesUiState (
    val isLoading: Boolean = false,
    val huacales: List<EntradaHucales> = emptyList(),
    val message: String? = null,
    val navigateToCreate: Boolean = false,
    val navigateToEditId: Int? = null
)