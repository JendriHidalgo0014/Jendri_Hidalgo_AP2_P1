package edu.ucne.jendri_hidalgo_ap2_p1.domain.repository

import edu.ucne.jendri_hidalgo_ap2_p1.domain.model.Huacales
import kotlinx.coroutines.flow.Flow

interface HuacalesRepository {
    fun observeHuacales(): Flow<List<Huacales>>
    suspend fun getHuacales(id: Int): Huacales?
    suspend fun upsert(huacales: Huacales): Int
    suspend fun delete(id:Int)
    suspend fun existePorNombre(nombrecliente: String): Boolean
}