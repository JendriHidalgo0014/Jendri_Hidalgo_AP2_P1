package edu.ucne.jendri_hidalgo_ap2_p1.domain.usecase

import java.time.LocalDate

private const val Campo = "Este campo es obligatorio"

data class CervezaValidationResult(
    val isValid: Boolean,
    val error: String? = null
)
fun validateNombre(value: String): CervezaValidationResult {
    if (value.isBlank()) return CervezaValidationResult(false, Campo)
    return CervezaValidationResult(true)
}

fun validateMarca(value: String): CervezaValidationResult {
    if (value.isBlank()) return CervezaValidationResult(false, Campo)
    return CervezaValidationResult(true)
}

fun validatePuntuacion(value: String): CervezaValidationResult {
    if (value.isBlank()) return CervezaValidationResult(false, Campo)
    val puntuacion = value.toDoubleOrNull() ?: return CervezaValidationResult(false, "Debe ingresar un número válido")
    if (puntuacion <= 0) return CervezaValidationResult(false, "La puntuación debe ser mayor a cero")
    return CervezaValidationResult(true)
}

fun validateNombreDuplicado(nombre: String, nombresExistentes: List<String>): CervezaValidationResult {
    if (nombresExistentes.contains(nombre)) {
        return CervezaValidationResult(false, "Ya existe una cerveza registrada con este nombre")
    }
    return CervezaValidationResult(true)
}