package edu.ucne.jendri_hidalgo_ap2_p1.domain.usecase

import edu.ucne.jendri_hidalgo_ap2_p1.domain.model.EntradaHucales

import edu.ucne.jendri_hidalgo_ap2_p1.domain.repository.HuacalesRepository

class GetHuacalesUseCase (
    private val repository: HuacalesRepository
) {
    suspend operator fun invoke(id: Int): EntradaHucales? = repository.getHuacales(id)
}