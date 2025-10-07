package edu.ucne.jendri_hidalgo_ap2_p1.presentation.tareas.edit

import android.app.DatePickerDialog
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange // Cambiado por DateRange
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

@Composable
fun EditHuacalesScreen(
    viewModel: EditHuacalesViewModel = hiltViewModel(),
    entradaId: Int,
    onSaveSuccess: () -> Unit = {},
    goBack: () -> Unit
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(entradaId) {
        viewModel.onEvent(EditHuacalesUiEvent.Load(entradaId))
    }

    if (state.saved) {
        LaunchedEffect(Unit) {
            onSaveSuccess()
        }
    }

    EditHuacalesBody(
        state = state,
        onEvent = viewModel::onEvent
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditHuacalesBody(
    state: EditHuacalesUiState,
    onEvent: (EditHuacalesUiEvent) -> Unit
) {
    var showDatePicker by remember { mutableStateOf(false) }
    val dateFormatter = remember { SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()) }

    Column(
        modifier = Modifier.padding(16.dp)
    ) {
        Spacer(Modifier.height(16.dp))
        // Campo de fecha que abre DatePicker
        OutlinedTextField(
            value = state.fecha?.let { dateFormatter.format(it) } ?: "Seleccionar fecha",
            onValueChange = { }, // No permitir edición directa
            label = { Text("Fecha de Entrada") },
            isError = state.fechaError != null,
            modifier = Modifier
                .fillMaxWidth()
                .testTag("input_fecha")
                .clickable { showDatePicker = true },
            readOnly = true,
            trailingIcon = {
                Icon(
                    imageVector = Icons.Default.DateRange, // Cambiado a DateRange
                    contentDescription = "Seleccionar fecha",
                    modifier = Modifier.clickable { showDatePicker = true }
                )
            }
        )
        if (state.fechaError != null) {
            Text(
                text = state.fechaError,
                color = MaterialTheme.colorScheme.error,
                modifier = Modifier.padding(start = 16.dp, top = 4.dp)
            )
        }

        // Mostrar DatePicker cuando sea necesario
        if (showDatePicker) {
            MyDatePickerDialog(
                onDateSelected = { date ->
                    onEvent(EditHuacalesUiEvent.FechaChanged(date))
                    showDatePicker = false
                },
                onDismiss = { showDatePicker = false }
            )
        }

        Spacer(Modifier.height(16.dp))

        OutlinedTextField(
            value = state.nombrecliente,
            onValueChange = { onEvent(EditHuacalesUiEvent.NombreChanged(it)) },
            label = { Text("Nombre del cliente") },
            isError = state.nombreclienteError != null,
            modifier = Modifier
                .fillMaxWidth()
                .testTag("input_nombrecliente")
        )
        if (state.nombreclienteError != null) {
            Text(
                text = state.nombreclienteError,
                color = MaterialTheme.colorScheme.error,
                modifier = Modifier.padding(start = 16.dp, top = 4.dp)
            )
        }

        Spacer(Modifier.height(12.dp))

        OutlinedTextField(
            value = state.cantidad,
            onValueChange = { onEvent(EditHuacalesUiEvent.CantidadChanged(it)) },
            label = { Text("Cantidad de huacales") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            isError = state.cantidadError != null,
            modifier = Modifier
                .fillMaxWidth()
                .testTag("input_cantidad")
        )
        if (state.cantidadError != null) {
            Text(
                text = state.cantidadError,
                color = MaterialTheme.colorScheme.error,
                modifier = Modifier.padding(start = 16.dp, top = 4.dp)
            )
        }

        Spacer(Modifier.height(12.dp))

        OutlinedTextField(
            value = state.precio,
            onValueChange = { onEvent(EditHuacalesUiEvent.PrecioChanged(it)) },
            label = { Text("Precio del huacal") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            isError = state.precioError != null,
            modifier = Modifier
                .fillMaxWidth()
                .testTag("input_precio")
        )
        if (state.precioError != null) {
            Text(
                text = state.precioError,
                color = MaterialTheme.colorScheme.error,
                modifier = Modifier.padding(start = 16.dp, top = 4.dp)
            )
        }

        Spacer(Modifier.height(16.dp))

        Row {
            Button(
                onClick = { onEvent(EditHuacalesUiEvent.Save) },
                enabled = !state.isSaving,
                modifier = Modifier
                    .weight(1f)
                    .testTag("btn_guardar")
            ) { Text("Guardar") }

            Spacer(Modifier.width(8.dp))

            if (!state.isNew) {
                OutlinedButton(
                    onClick = { onEvent(EditHuacalesUiEvent.Delete) },
                    enabled = !state.isDeleting,
                    modifier = Modifier.testTag("btn_eliminar")
                ) { Text("Eliminar") }
            }
        }
    }
}

@Composable
fun MyDatePickerDialog(
    onDateSelected: (Date) -> Unit,
    onDismiss: () -> Unit
) {
    val context = LocalContext.current
    val calendar = Calendar.getInstance()
    val year = calendar.get(Calendar.YEAR)
    val month = calendar.get(Calendar.MONTH)
    val day = calendar.get(Calendar.DAY_OF_MONTH)

    DisposableEffect(Unit) {
        val datePickerDialog = DatePickerDialog(
            context,
            { _, selectedYear, selectedMonth, selectedDay ->
                val selectedDate = Calendar.getInstance().apply {
                    set(selectedYear, selectedMonth, selectedDay)
                }.time
                onDateSelected(selectedDate)
            },
            year,
            month,
            day
        )

        datePickerDialog.show()

        datePickerDialog.setOnDismissListener {
            onDismiss()
        }

        onDispose {
            datePickerDialog.dismiss()
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun EditHuacalesBodyPreview() {
    val state = EditHuacalesUiState()
    MaterialTheme {
        EditHuacalesBody(state = state) {}
    }
}