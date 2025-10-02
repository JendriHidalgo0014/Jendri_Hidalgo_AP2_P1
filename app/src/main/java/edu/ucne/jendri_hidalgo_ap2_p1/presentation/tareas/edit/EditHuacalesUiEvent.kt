package edu.ucne.jendri_hidalgo_ap2_p1.presentation.tareas.edit

sealed interface EditHuacalesUiEvent {
    data class Load(val id: Int?) : EditHuacalesUiEvent
    data class NombresChanged(val value: String) : EditHuacalesUiEvent
    data class CantidadChanged(val value: String) : EditHuacalesUiEvent
    data class PrecioChanged(val value: String) : EditHuacalesUiEvent

    data object Save : EditHuacalesUiEvent
    data object Delete : EditHuacalesUiEvent
}