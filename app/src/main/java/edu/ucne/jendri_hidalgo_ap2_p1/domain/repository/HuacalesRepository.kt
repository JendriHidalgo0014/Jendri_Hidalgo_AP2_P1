package edu.ucne.jendri_hidalgo_ap2_p1.domain.repository

import edu.ucne.jendri_hidalgo_ap2_p1.domain.model.EntradaHucales
import kotlinx.coroutines.flow.Flow

interface HuacalesRepository {
    fun observeHuacales(): Flow<List<EntradaHucales>>
    suspend fun getHuacales(id: Int): EntradaHucales?
    suspend fun upsert(huacales: EntradaHucales): Int
    suspend fun delete(id:Int)
    suspend fun existePorNombre(nombrecliente: String): Boolean
}