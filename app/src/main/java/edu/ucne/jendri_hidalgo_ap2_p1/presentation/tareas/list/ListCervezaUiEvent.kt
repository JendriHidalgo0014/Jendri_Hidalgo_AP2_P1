package edu.ucne.jendri_hidalgo_ap2_p1.presentation.tareas.list

sealed interface ListCervezaUiEvent {
    data object Load : ListCervezaUiEvent
    data class Delete(val id: Int) : ListCervezaUiEvent
    data object CreateNew : ListCervezaUiEvent
    data class Edit(val id: Int) : ListCervezaUiEvent
    data class ShowMessage(val message: String) : ListCervezaUiEvent
    data class FiltroNombreChanged(val nombre: String) : ListCervezaUiEvent
    data class FiltroMarcaChanged(val marca: String) : ListCervezaUiEvent
}