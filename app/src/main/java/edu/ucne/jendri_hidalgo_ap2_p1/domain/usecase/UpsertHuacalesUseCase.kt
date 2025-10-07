package edu.ucne.jendri_hidalgo_ap2_p1.domain.usecase

import edu.ucne.jendri_hidalgo_ap2_p1.domain.model.EntradaHucales
import edu.ucne.jendri_hidalgo_ap2_p1.domain.repository.HuacalesRepository
import kotlinx.coroutines.flow.first

class UpsertHuacalesUseCase(
    private val repository: HuacalesRepository
) {
    suspend operator fun invoke(huacales: EntradaHucales): Result<Int> {
        // Validación para el modelo de dominio (con Int)
        if (huacales.fecha == null) {
            return Result.failure(IllegalArgumentException("La fecha es requerida"))
        }
        if (huacales.nombrecliente.isBlank()) {
            return Result.failure(IllegalArgumentException("El nombre del cliente es requerido"))
        }
        if (huacales.cantidad <= 0) {
            return Result.failure(IllegalArgumentException("La cantidad debe ser mayor a 0"))
        }
        if (huacales.precio <= 0) {
            return Result.failure(IllegalArgumentException("El precio debe ser mayor a 0"))
        }

        return runCatching {
            repository.upsert(huacales)
        }
    }
}