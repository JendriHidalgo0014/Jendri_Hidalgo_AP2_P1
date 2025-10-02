package edu.ucne.jendri_hidalgo_ap2_p1.presentation.tareas.edit

class EditHuacalesUiState (
    val entradaId: Int? = null,
    val fecha: String = "",
    val nombrecliente: String = "",
    val cantidad: String = "",
    val precio: String = "",
    val nombreclienteError: String? = null,
    val fechaError: String? = null,
    val cantidadError: String? = null,
    val precioError: String? = null,
    val isSaving: Boolean = false,
    val isDeleting: Boolean = false,
    val isNew: Boolean = true,
    val saved: Boolean = false,
    val deleted: Boolean=false,
    val saveError: String? = null
)