package edu.ucne.jendri_hidalgo_ap2_p1.presentation.tareas.list

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import edu.ucne.jendri_hidalgo_ap2_p1.domain.model.Cervezas

@Composable
fun ListCervezaScreen(
    viewModel: ListCervezaViewModel = hiltViewModel(),
    onDrawer: () -> Unit,
    onCreate: () -> Unit,
    onEdit: (Int) -> Unit
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.onEvent(ListCervezaUiEvent.Load)
    }

    ListCervezaBody(
        state = state,
        onDrawer = onDrawer,
        onCreate = onCreate,
        onEdit = onEdit,
        onDelete = { viewModel.onEvent(ListCervezaUiEvent.Delete(it)) }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ListCervezaBody(
    state: ListCervezaUiState,
    onDrawer: () -> Unit,
    onCreate: () -> Unit,
    onEdit: (Int) -> Unit,
    onDelete: (Int) -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Consulta de Cervezas") },
                navigationIcon = {
                    IconButton(onClick = onDrawer) {
                        Icon(Icons.Filled.Menu, contentDescription = "Menú")
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { onCreate() }
            ) {
                Icon(Icons.Default.Add, contentDescription = "Crear")
            }
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(16.dp)
        ) {
            if (state.isLoading) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            } else if (state.cervezas.isEmpty()) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "No hay cervezas registrados",
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            } else {
                LazyColumn {
                    items(state.cervezas) { cerveza ->
                        CervezaCard(
                            cerveza = cerveza,
                            onClick = { onEdit(cerveza.cervezaId) },
                            onDelete = { onDelete(cerveza.cervezaId) }
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun CervezaCard(
    cerveza: Cervezas,
    onClick: () -> Unit,
    onDelete: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .clickable { onClick() }
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = cerveza.nombre,
                    style = MaterialTheme.typography.titleMedium
                )
                Text("Marca: ${cerveza.marca}")
                Text("Puntuacion: $${String.format("%.2f", cerveza.puntuacion)}")
            }
            TextButton(onClick = { onDelete() }) {
                Text("Eliminar")
            }
        }
    }
}

@Preview
@Composable
private fun ListCervezaBodyPreview() {
    val state = ListCervezaUiState()
    MaterialTheme {
        ListCervezaBody(
            state = state,
            onDrawer = {},
            onCreate = {},
            onEdit = {},
            onDelete = {}
        )
    }
}