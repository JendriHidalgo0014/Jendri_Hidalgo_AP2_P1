package edu.ucne.jendri_hidalgo_ap2_p1.presentation.tareas.edit

import java.util.Date

data class EditHuacalesUiState(
    val entradaId: Int = 0,
    val fecha: Date? = null,
    val nombrecliente: String = "",
    val cantidad: String = "",
    val precio: String = "",
    val fechaError: String? = null,
    val nombreclienteError: String? = null,
    val cantidadError: String? = null,
    val precioError: String? = null,
    val isSaving: Boolean = false,
    val saved: Boolean = false,
    val isNew: Boolean = true,
    val isDeleting: Boolean = false
)