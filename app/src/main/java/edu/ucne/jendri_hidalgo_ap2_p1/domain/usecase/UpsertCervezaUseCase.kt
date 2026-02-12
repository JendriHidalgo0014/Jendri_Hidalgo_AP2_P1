package edu.ucne.jendri_hidalgo_ap2_p1.domain.usecase

import edu.ucne.jendri_hidalgo_ap2_p1.domain.model.Cervezas
import edu.ucne.jendri_hidalgo_ap2_p1.domain.repository.CervezaRepository


class UpsertCervezaUseCase(
    private val repository: CervezaRepository
) {
    suspend operator fun invoke(cervezas: Cervezas, nombresExistentes: List<String>): Result<Unit> {
        val nomb = validateNombre(cervezas.nombre)
        if (!nomb.isValid) return Result.failure(IllegalArgumentException(nomb.error))

        val mar = validateMarca(cervezas.marca .toString())
        if (!mar.isValid) return Result.failure(IllegalArgumentException(mar.error))

        val punt = validatePuntuacion(cervezas.puntuacion.toString())
        if (!punt.isValid) return Result.failure(IllegalArgumentException(punt.error))

        val duplic = validateNombreDuplicado(cervezas.nombre, nombresExistentes)
        if (!duplic.isValid) return Result.failure(IllegalArgumentException(duplic.error))

        return runCatching { repository.upsert(cervezas) }
    }
}