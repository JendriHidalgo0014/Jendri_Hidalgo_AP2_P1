package edu.ucne.jendri_hidalgo_ap2_p1.presentation.tareas.list

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import edu.ucne.jendri_hidalgo_ap2_p1.domain.model.EntradaHucales
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ListHuacalesScreen(
    viewModel: ListHuacalesViewModel = hiltViewModel(),
    onNavigateToCreate: (Int) -> Unit,
    onNavigateToEdit: (Int) -> Unit
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    if (state.navigateToCreate) {
        onNavigateToCreate(0)
        viewModel.onNavigationHandled()
    }

    state.navigateToEditId?.let { id ->
        onNavigateToEdit(id)
        viewModel.onNavigationHandled()
    }

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Entrada de Huacales") })
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { viewModel.onEvent(ListHuacalesUiEvent.CreateNew) },
                modifier = Modifier.testTag("fab_create_huacales")
            ) { Text("+") }
        }
    ) { padding ->
        if (state.isLoading) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(modifier = Modifier.testTag("loading"))
            }
        } else if (state.huacales.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding),
                contentAlignment = Alignment.Center
            ) { Text("No hay huacales agregados") }
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .testTag("huacales_list")
            ) {
                items(state.huacales) { huacales ->
                    EntradaHuacalesCard(
                        huacales = huacales,
                        onClick = { viewModel.onEvent(ListHuacalesUiEvent.Edit(huacales.entradaId)) },
                        onDelete = { viewModel.onEvent(ListHuacalesUiEvent.Delete(huacales.entradaId)) }
                    )
                }
            }
        }
    }
}

@Composable
fun EntradaHuacalesCard(
    huacales: EntradaHucales,
    onClick: () -> Unit,
    onDelete: () -> Unit
) {
    val dateFormatter = remember { SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()) }
    val currencyFormatter = remember { NumberFormat.getCurrencyInstance(Locale.getDefault()) }
    val total = huacales.cantidad * huacales.precio

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp, horizontal = 16.dp)
            .clickable { onClick() }
            .testTag("huacales_card_${huacales.entradaId}")
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.Top,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(modifier = Modifier.weight(1f)) {
                // Título principal con nombre del cliente
                Text(
                    text = huacales.nombrecliente,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.height(8.dp))

                // Fecha formateada correctamente
                Text(
                    text = " ${dateFormatter.format(huacales.fecha)}",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold

                )

                Spacer(modifier = Modifier.height(4.dp))

                // Detalles en una sola línea
                Text(
                    text = " ${huacales.cantidad} x ${currencyFormatter.format(huacales.precio.toDouble())}",
                    style = MaterialTheme.typography.bodyMedium
                )

                Spacer(modifier = Modifier.height(8.dp))

                // Total destacado
                Text(
                    text = "= ${currencyFormatter.format(total.toDouble())}",
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.primary,
                    fontWeight = FontWeight.Bold
                )
            }

        }
    }
}