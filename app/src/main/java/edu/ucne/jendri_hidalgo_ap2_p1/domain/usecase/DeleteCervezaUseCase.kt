package edu.ucne.jendri_hidalgo_ap2_p1.domain.usecase

import edu.ucne.jendri_hidalgo_ap2_p1.domain.repository.CervezaRepository

class DeleteCervezaUseCase  (
private val repository: CervezaRepository
) {
    suspend operator fun invoke(id: Int) = repository.delete(id)
}
