package edu.ucne.jendri_hidalgo_ap2_p1.presentation.tareas.edit

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import kotlinx.coroutines.launch

@Composable
fun EditCervezaScreen(
    cervezaId: Int?,
    onDrawer: () -> Unit,
    goBack: () -> Unit,
    viewModel: EditCervezaViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val scope = rememberCoroutineScope()

    LaunchedEffect(cervezaId) {
        viewModel.onEvent(EditCervezaUiEvent.Load(cervezaId))
    }

    EditCervezaBody(
        state = state,
        onEvent = { event ->
            scope.launch {
                val success = viewModel.onEvent(event)
                if (event is EditCervezaUiEvent.Save && success) {
                    goBack()
                } else if (event is EditCervezaUiEvent.Delete && success) {
                    goBack()
                }
            }
        },
        onDrawer = onDrawer,
        goBack = goBack
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun EditCervezaBody(
    state: EditCervezaUiState,
    onEvent: (EditCervezaUiEvent) -> Unit,
    onDrawer: () -> Unit,
    goBack: () -> Unit
) {

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(if (state.isNew) "Nueva Cerveza" else "Modificar") },
                navigationIcon = {
                    IconButton(onClick = goBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Volver")
                    }
                },
                actions = {
                    IconButton(onClick = onDrawer) {
                        Icon(Icons.Filled.Menu, contentDescription = "Menú")
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(16.dp)
        ) {
            OutlinedTextField(
                value = state.nombre,
                onValueChange = { onEvent(EditCervezaUiEvent.NombreChanged(it)) },
                label = { Text("Nombre de la cerveza") },
                isError = state.nombreError != null,
                modifier = Modifier.fillMaxWidth()
            )
            if (state.nombreError != null) {
                Text(
                    state.nombreError,
                    color = MaterialTheme.colorScheme.error
                )
            }
            Spacer(Modifier.height(12.dp))

            OutlinedTextField(
                value = state.marca,
                onValueChange = { onEvent(EditCervezaUiEvent.MarcaChanged(it)) },
                label = { Text("Marca") },
                isError = state.marcaError != null,
                modifier = Modifier.fillMaxWidth()
            )
            if (state.marcaError != null) {
                Text(
                    state.marcaError,
                    color = MaterialTheme.colorScheme.error
                )
            }
            Spacer(Modifier.height(12.dp))
            OutlinedTextField(
                value = state.puntuacion,
                onValueChange = { onEvent(EditCervezaUiEvent.PuntuacionChanged(it)) },
                label = { Text("Puntuacion") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                isError = state.puntuacionError != null,
                modifier = Modifier.fillMaxWidth()
            )
            if (state.puntuacionError != null) {
                Text(
                    state.puntuacionError,
                    color = MaterialTheme.colorScheme.error
                )
            }

            Spacer(Modifier.height(16.dp))
            Row {
                Button(
                    onClick = { onEvent(EditCervezaUiEvent.Save) },
                    enabled = !state.isSaving
                ) { Text("Guardar") }
                Spacer(Modifier.width(8.dp))
                if (!state.isNew) {
                    OutlinedButton(
                        onClick = { onEvent(EditCervezaUiEvent.Delete) },
                        enabled = !state.isDeleting
                    ) { Text("Eliminar") }
                }
            }
        }
    }
}

@Preview
@Composable
private fun EditCervezaBodyPreview() {
    val state = EditCervezaUiState()
    MaterialTheme {
        EditCervezaBody(state = state, onEvent = {}, onDrawer = {}, goBack = {})
    }
}