package edu.ucne.jendri_hidalgo_ap2_p1.domain.usecase

import edu.ucne.jendri_hidalgo_ap2_p1.domain.model.Cervezas
import edu.ucne.jendri_hidalgo_ap2_p1.domain.repository.CervezaRepository
import kotlinx.coroutines.flow.Flow

class ObserveCervezaUseCase (
    private val repository: CervezaRepository
) {
    operator fun invoke(): Flow<List<Cervezas>> = repository.observeCervezas()
}