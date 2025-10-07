package edu.ucne.jendri_hidalgo_ap2_p1.presentation.tareas.edit

import java.util.Date

sealed class EditHuacalesUiEvent {
    data class Load(val id: Int) : EditHuacalesUiEvent()
    data class FechaChanged(val fecha: Date) : EditHuacalesUiEvent()
    data class NombreChanged(val nombre: String) : EditHuacalesUiEvent()
    data class CantidadChanged(val cantidad: String) : EditHuacalesUiEvent()
    data class PrecioChanged(val precio: String) : EditHuacalesUiEvent()
    object Save : EditHuacalesUiEvent()
    object Delete : EditHuacalesUiEvent()
}