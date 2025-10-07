package edu.ucne.jendri_hidalgo_ap2_p1.presentation.tareas.edit

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import edu.ucne.jendri_hidalgo_ap2_p1.domain.model.EntradaHucales
import edu.ucne.jendri_hidalgo_ap2_p1.domain.usecase.DeleteHuacalesUseCase
import edu.ucne.jendri_hidalgo_ap2_p1.domain.usecase.GetHuacalesUseCase
import edu.ucne.jendri_hidalgo_ap2_p1.domain.usecase.UpsertHuacalesUseCase
import edu.ucne.jendri_hidalgo_ap2_p1.domain.usecase.validateHuacalesUi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.Date
import javax.inject.Inject

@HiltViewModel
class EditHuacalesViewModel @Inject constructor(
    private val getHuacalesUseCase: GetHuacalesUseCase,
    private val upsertHuacalesUseCase: UpsertHuacalesUseCase,
    private val deleteHuacalesUseCase: DeleteHuacalesUseCase
) : ViewModel() {
    private val _state = MutableStateFlow(EditHuacalesUiState())
    val state: StateFlow<EditHuacalesUiState> = _state.asStateFlow()

    fun onEvent(event: EditHuacalesUiEvent) {
        when (event) {
            is EditHuacalesUiEvent.Load -> onLoad(event.id)
            is EditHuacalesUiEvent.FechaChanged -> _state.update {
                it.copy(fecha = event.fecha, fechaError = null)
            }
            is EditHuacalesUiEvent.NombreChanged -> _state.update {
                it.copy(nombrecliente = event.nombre, nombreclienteError = null)
            }
            is EditHuacalesUiEvent.CantidadChanged -> _state.update {
                it.copy(cantidad = event.cantidad, cantidadError = null)
            }
            is EditHuacalesUiEvent.PrecioChanged -> _state.update {
                it.copy(precio = event.precio, precioError = null)
            }
            EditHuacalesUiEvent.Save -> onSave()
            EditHuacalesUiEvent.Delete -> onDelete()
        }
    }

    private fun onLoad(id: Int) {
        if (id == 0) {
            _state.update { it.copy(isNew = true, entradaId = 0) }
            return
        }
        viewModelScope.launch {
            val huacales = getHuacalesUseCase(id)
            huacales?.let {
                _state.update {
                    it.copy(
                        isNew = false,
                        entradaId = huacales.entradaId,
                        fecha = huacales.fecha,
                        nombrecliente = huacales.nombrecliente,
                        cantidad = huacales.cantidad.toString(),
                        precio = huacales.precio.toString()
                    )
                }
            }
        }
    }

    private fun onSave() {
        val fecha = state.value.fecha
        val nombrecliente = state.value.nombrecliente
        val cantidad = state.value.cantidad
        val precio = state.value.precio

        // Usar la función de validación del use case
        val validation = validateHuacalesUi(
            fecha = fecha,
            nombrecliente = nombrecliente,
            cantidad = cantidad,
            precio = precio
        )

        if (!validation.isValid) {
            _state.update {
                it.copy(
                    fechaError = validation.fechaError,
                    nombreclienteError = validation.nombresError,
                    cantidadError = validation.cantidadError,
                    precioError = validation.precioError
                )
            }
            return
        }

        viewModelScope.launch {
            _state.update { it.copy(isSaving = true) }
            try {
                val huacales = EntradaHucales(
                    entradaId = state.value.entradaId,
                    fecha = fecha ?: Date(), // Usar fecha actual si es null
                    nombrecliente = nombrecliente,
                    cantidad = cantidad.toInt(),
                    precio = precio.toInt()
                )

                val result = upsertHuacalesUseCase(huacales)

                if (result.isSuccess) {
                    val newId = result.getOrNull() ?: 0
                    _state.update {
                        it.copy(
                            isSaving = false,
                            saved = true,
                            entradaId = newId
                        )
                    }
                } else {
                    val exception = result.exceptionOrNull()
                    _state.update {
                        it.copy(
                            isSaving = false,
                            fechaError = exception?.message ?: "Error al guardar"
                        )
                    }
                }
            } catch (e: Exception) {
                _state.update {
                    it.copy(
                        isSaving = false,
                        fechaError = e.message ?: "Error inesperado al guardar"
                    )
                }
            }
        }
    }

    private fun onDelete() {
        val id = state.value.entradaId
        if (id == 0) return

        viewModelScope.launch {
            _state.update { it.copy(isDeleting = true) }
            try {
                deleteHuacalesUseCase(id)
                _state.update { it.copy(isDeleting = false, saved = true) }
            } catch (e: Exception) {
                _state.update {
                    it.copy(
                        isDeleting = false,
                        fechaError = "Error al eliminar: ${e.message}"
                    )
                }
            }
        }
    }
}