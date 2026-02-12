package edu.ucne.jendri_hidalgo_ap2_p1.presentation.tareas.edit

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import edu.ucne.jendri_hidalgo_ap2_p1.domain.model.Cervezas
import edu.ucne.jendri_hidalgo_ap2_p1.domain.usecase.DeleteCervezaUseCase
import edu.ucne.jendri_hidalgo_ap2_p1.domain.usecase.GetCervezaUseCase
import edu.ucne.jendri_hidalgo_ap2_p1.domain.usecase.GetCervezasUseCase
import edu.ucne.jendri_hidalgo_ap2_p1.domain.usecase.UpsertCervezaUseCase
import edu.ucne.jendri_hidalgo_ap2_p1.domain.usecase.validateMarca
import edu.ucne.jendri_hidalgo_ap2_p1.domain.usecase.validateNombre
import edu.ucne.jendri_hidalgo_ap2_p1.domain.usecase.validatePuntuacion
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EditCervezaViewModel @Inject constructor(
    private val getCervezaUseCase: GetCervezaUseCase,
    private val getCervezasUseCase: GetCervezasUseCase,
    private val upsertCervezasUseCase: UpsertCervezaUseCase,
    private val deleteCervezaUseCase: DeleteCervezaUseCase
) : ViewModel() {
    private val _state = MutableStateFlow(EditCervezaUiState())
    val state: StateFlow<EditCervezaUiState> = _state.asStateFlow()

    suspend fun onEvent(event: EditCervezaUiEvent): Boolean {
        when (event) {
            is EditCervezaUiEvent.Load -> {
                onLoad(event.id)
                return true
            }

            is EditCervezaUiEvent.NombreChanged -> {
                _state.update {
                    it.copy(
                        nombre = event.value,
                        nombreError = null
                    )
                }
                return true
            }

            is EditCervezaUiEvent.MarcaChanged -> {
                _state.update {
                    it.copy(
                        marca = event.value,
                        marcaError = null
                    )
                }
                return true
            }

            is EditCervezaUiEvent.PuntuacionChanged -> {
                _state.update {
                    it.copy(
                        puntuacion = event.value,
                        puntuacionError = null
                    )
                }
                return true
            }

            EditCervezaUiEvent.Save -> return onSave()
            EditCervezaUiEvent.Delete -> return onDelete()
        }
    }

    private fun onLoad(id: Int?) {
        if (id == null || id == 0) {
            _state.update { it.copy(isNew = true, cervezaId = null) }
            return
        }
        viewModelScope.launch {
            val cerveza = getCervezaUseCase(id)
            if (cerveza != null) {
                _state.update {
                    it.copy(
                        isNew = false,
                        cervezaId = cerveza.cervezaId,
                        nombre = cerveza.nombre,
                        marca = cerveza.marca,
                        puntuacion = cerveza.puntuacion.toString()
                    )
                }
            }
        }
    }

    private suspend fun onSave(): Boolean {
        val nombre = state.value.nombre
        val marca = state.value.marca
        val puntuacion = state.value.puntuacion

        val n = validateNombre(nombre)
        val c = validateMarca(marca)
        val p = validatePuntuacion(puntuacion)

        if (!n.isValid || !c.isValid || !p.isValid) {
            _state.update {
                it.copy(
                    nombreError = n.error,
                    marcaError = c.error,
                    puntuacionError = p.error,
                )
            }
            return false
        }

        _state.update { it.copy(isSaving = true) }

        val id = state.value.cervezaId ?: 0
        val cerveza = Cervezas(
            cervezaId = id,
            nombre = nombre,
            marca = marca,
            puntuacion = puntuacion.toDouble(),
        )

        val nombresExistentes = getCervezasUseCase().first()
            .filter { it.cervezaId != id }
            .map { it.nombre }

        val result = upsertCervezasUseCase(cerveza, nombresExistentes)

        return result.onSuccess {
            _state.update { it.copy(isSaving = false, saved = true) }
        }.onFailure { error ->
            _state.update {
                it.copy(
                    isSaving = false,
                    nombreError = error.message
                )
            }
        }.isSuccess
    }

    private suspend fun onDelete(): Boolean {
        val id = state.value.cervezaId ?: return false
        _state.update { it.copy(isDeleting = true) }
        deleteCervezaUseCase(id)
        _state.update { it.copy(isDeleting = false, deleted = true) }
        return true
    }

}

