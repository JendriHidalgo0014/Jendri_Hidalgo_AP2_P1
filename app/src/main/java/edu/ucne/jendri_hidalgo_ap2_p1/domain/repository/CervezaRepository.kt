package edu.ucne.jendri_hidalgo_ap2_p1.domain.repository

import edu.ucne.jendri_hidalgo_ap2_p1.domain.model.Cervezas
import kotlinx.coroutines.flow.Flow

interface CervezaRepository {
    fun observeCervezas(): Flow<List<Cervezas>>
    suspend fun getCerveza(id: Int): Cervezas?
    suspend fun upsert(cervezas: Cervezas): Int
    suspend fun delete(id: Int)
}