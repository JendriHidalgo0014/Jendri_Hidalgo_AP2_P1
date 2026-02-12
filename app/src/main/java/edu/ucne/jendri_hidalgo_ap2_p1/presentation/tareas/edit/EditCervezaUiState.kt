package edu.ucne.jendri_hidalgo_ap2_p1.presentation.tareas.edit


data class EditCervezaUiState(
    val cervezaId: Int? = null,
    val nombre: String = "",
    val marca: String = "",
    val puntuacion: String = "",
    val nombreError: String? = null,
    val marcaError: String? = null,
    val puntuacionError: String? = null,
    val isSaving: Boolean = false,
    val isDeleting: Boolean = false,
    val isNew: Boolean = true,
    val saved: Boolean = false,
    val deleted: Boolean = false
)