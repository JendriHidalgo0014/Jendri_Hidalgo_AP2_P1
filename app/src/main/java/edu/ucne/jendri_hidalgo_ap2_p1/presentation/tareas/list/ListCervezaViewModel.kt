package edu.ucne.jendri_hidalgo_ap2_p1.presentation.tareas.list

import androidx.lifecycle.ViewModel
import edu.ucne.jendri_hidalgo_ap2_p1.domain.usecase.DeleteCervezaUseCase
import edu.ucne.jendri_hidalgo_ap2_p1.domain.usecase.GetCervezasUseCase
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ListCervezaViewModel @Inject constructor(
    private val getCervezasUseCase: GetCervezasUseCase,
    private val deleteCervezaUseCase: DeleteCervezaUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(ListCervezaUiState())
    val state: StateFlow<ListCervezaUiState> = _state.asStateFlow()

    init {
        onEvent(ListCervezaUiEvent.Load)
    }

    fun onEvent(event: ListCervezaUiEvent) {
        when (event) {
            ListCervezaUiEvent.Load -> onLoad()
            is ListCervezaUiEvent.Delete -> onDelete(event.id)
            ListCervezaUiEvent.CreateNew -> _state.update { it.copy(navigateToCreate = true) }
            is ListCervezaUiEvent.Edit -> _state.update { it.copy(navigateToEditId = event.id) }
            is ListCervezaUiEvent.ShowMessage -> _state.update { it.copy(message = event.message) }
        }
    }

    private fun onLoad() {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }
            getCervezasUseCase().collect { cervezas ->
                _state.update {
                    it.copy(
                        isLoading = false,
                        cervezas = cervezas
                    )
                }
            }
        }
    }

    private fun onDelete(id: Int) {
        viewModelScope.launch {
            deleteCervezaUseCase(id)
            _state.update { it.copy(message = "Cerveza eliminada") }
        }
    }
}