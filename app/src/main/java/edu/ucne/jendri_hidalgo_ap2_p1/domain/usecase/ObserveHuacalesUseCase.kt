package edu.ucne.jendri_hidalgo_ap2_p1.domain.usecase

import edu.ucne.jendri_hidalgo_ap2_p1.domain.repository.HuacalesRepository
import kotlinx.coroutines.flow.Flow

class ObserveHuacalesUseCase(
    private val repository: HuacalesRepository
) {
    operator fun invoke(): Flow<List<edu.ucne.jendri_hidalgo_ap2_p1.domain.model.EntradaHucales>> = repository.observeHuacales()
}