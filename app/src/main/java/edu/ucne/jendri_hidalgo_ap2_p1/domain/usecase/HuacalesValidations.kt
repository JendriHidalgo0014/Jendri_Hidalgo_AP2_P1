package edu.ucne.jendri_hidalgo_ap2_p1.domain.usecase

import java.util.Date


data class ValidationResult(
    val isValid: Boolean,
    val fechaError: String? = null,
    val nombresError: String? = null,
    val cantidadError: String? = null,
    val precioError: String? = null
)

fun validateHuacalesUi(
    fecha: Date?,
    nombrecliente: String,
    cantidad: String,
    precio: String
): ValidationResult {
    var isValid = true
    var fechaError: String? = null
    var nombresError: String? = null
    var cantidadError: String? = null
    var precioError: String? = null

    if (fecha == null) {
        isValid = false
        fechaError = "La fecha es requerida"
    }

    if (nombrecliente.isBlank()) {
        isValid = false
        nombresError = "El nombre del cliente es requerido"
    }

    val cantidadInt = cantidad.toIntOrNull()
    if (cantidadInt == null || cantidadInt <= 0) {
        isValid = false
        cantidadError = "La cantidad debe ser un número mayor a 0"
    }

    val precioInt = precio.toIntOrNull()
    if (precioInt == null || precioInt <= 0) {
        isValid = false
        precioError = "El precio debe ser un número mayor a 0"
    }

    return ValidationResult(
        isValid = isValid,
        fechaError = fechaError,
        nombresError = nombresError,
        cantidadError = cantidadError,
        precioError = precioError
    )
}