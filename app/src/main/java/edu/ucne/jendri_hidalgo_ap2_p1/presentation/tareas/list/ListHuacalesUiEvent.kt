package edu.ucne.jendri_hidalgo_ap2_p1.presentation.tareas.list

sealed interface ListHuacalesUiEvent {
    data object Load : ListHuacalesUiEvent
    data class Delete(val entradaId: Int) : ListHuacalesUiEvent
    data object CreateNew : ListHuacalesUiEvent
    data class Edit(val entradaId: Int) : ListHuacalesUiEvent

    data class ShowMessage(val message: String) : ListHuacalesUiEvent
}