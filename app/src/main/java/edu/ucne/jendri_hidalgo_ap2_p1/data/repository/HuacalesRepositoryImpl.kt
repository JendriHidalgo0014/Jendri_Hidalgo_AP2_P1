package edu.ucne.jendri_hidalgo_ap2_p1.data.repository

import edu.ucne.jendri_hidalgo_ap2_p1.domain.model.EntradaHucales
import edu.ucne.jendri_hidalgo_ap2_p1.data.local.Dao.HucalesDao
import edu.ucne.jendri_hidalgo_ap2_p1.data.local.mapper.toDomain
import edu.ucne.jendri_hidalgo_ap2_p1.data.local.mapper.toEntity
import edu.ucne.jendri_hidalgo_ap2_p1.domain.repository.HuacalesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class HuacalesRepositoryImpl @Inject constructor(
    private val dao: HucalesDao
) : HuacalesRepository {

    override fun observeHuacales(): Flow<List<EntradaHucales>> = dao.observeAll().map { list ->
        list.map { it.toDomain() } // Agregar paréntesis ()
    }

    override suspend fun getHuacales(id: Int): EntradaHucales? =
        dao.getById(id)?.toDomain() // Agregar paréntesis ()

    override suspend fun upsert(huacales: EntradaHucales): Int {
        dao.upsert(huacales.toEntity()) // Agregar paréntesis ()
        return huacales.entradaId // Esta propiedad debería estar disponible
    }

    override suspend fun delete(id: Int) {
        dao.deleteById(id)
    }

    override suspend fun existePorNombre(nombrecliente: String): Boolean =
        dao.existePorNombre(nombrecliente)
}