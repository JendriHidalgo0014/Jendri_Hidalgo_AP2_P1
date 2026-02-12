package edu.ucne.jendri_hidalgo_ap2_p1.data.repository


import edu.ucne.jendri_hidalgo_ap2_p1.data.local.dao.CervezaDao
import edu.ucne.jendri_hidalgo_ap2_p1.data.local.mapper.toDomain
import edu.ucne.jendri_hidalgo_ap2_p1.data.local.mapper.toEntity
import edu.ucne.jendri_hidalgo_ap2_p1.domain.model.Cervezas
import edu.ucne.jendri_hidalgo_ap2_p1.domain.repository.CervezaRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class CervezaRepositoryImpl (
    private val dao: CervezaDao
) : CervezaRepository {
    override fun observeCervezas(): Flow<List<Cervezas>> = dao.observeAll().map { list ->
        list.map {it.toDomain()}
    }

    override suspend fun getCerveza(id: Int): Cervezas? = dao.getById(id)?.toDomain()

    override suspend fun upsert(cervezas: Cervezas): Int {
        dao.upsert(cervezas.toEntity())
        return cervezas.cervezaId
    }

    override suspend fun delete(id: Int) {
        dao.deleteById(id)
    }
}