package edu.ucne.jendri_hidalgo_ap2_p1.domain.usecase

import edu.ucne.jendri_hidalgo_ap2_p1.domain.repository.HuacalesRepository

class DeleteHuacalesUseCase(
    private val repository: HuacalesRepository
) {
    suspend operator fun invoke(id: Int) = repository.delete(id)
}