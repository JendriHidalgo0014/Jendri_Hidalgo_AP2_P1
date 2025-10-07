package edu.ucne.jendri_hidalgo_ap2_p1.presentation.tareas.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import edu.ucne.jendri_hidalgo_ap2_p1.domain.usecase.DeleteHuacalesUseCase
import edu.ucne.jendri_hidalgo_ap2_p1.domain.usecase.ObserveHuacalesUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ListHuacalesViewModel @Inject constructor(
    private val observeHuacalesUseCase: ObserveHuacalesUseCase,
    private val deleteHuacalesUseCase: DeleteHuacalesUseCase
) : ViewModel() {
    private val _state = MutableStateFlow(ListHuacalesUiState(isLoading = true))
    val state: StateFlow<ListHuacalesUiState> = _state.asStateFlow()

    init {
        onEvent(ListHuacalesUiEvent.Load)
    }

    fun onEvent(event: ListHuacalesUiEvent) {
        when (event) {
            is ListHuacalesUiEvent.Load -> observe()
            is ListHuacalesUiEvent.Delete -> onDelete(event.entradaId)
            ListHuacalesUiEvent.CreateNew -> _state.update { it.copy(navigateToCreate = true) }
            is ListHuacalesUiEvent.Edit -> _state.update { it.copy(navigateToEditId = event.entradaId) }
            is ListHuacalesUiEvent.ShowMessage -> _state.update { it.copy(message = event.message) }
        }
    }

    private fun observe() {
        viewModelScope.launch {
            observeHuacalesUseCase().collectLatest { list ->
                _state.update { it.copy(isLoading = false, huacales = list, message = null) }
            }
        }
    }

    private fun onDelete(id: Int) {
        viewModelScope.launch {
            try {
                deleteHuacalesUseCase(id)
                onEvent(ListHuacalesUiEvent.ShowMessage("Huacal eliminado con éxito"))
            } catch (e: Exception) {
                onEvent(
                    ListHuacalesUiEvent.ShowMessage(
                        e.message ?: "Error!! no se pudo eliminar el Huacal"
                    )
                )
            }
        }
    }

    fun onNavigationHandled() {
        _state.update { it.copy(navigateToCreate = false, navigateToEditId = null) }
    }
}