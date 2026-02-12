package edu.ucne.jendri_hidalgo_ap2_p1.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import edu.ucne.jendri_hidalgo_ap2_p1.data.local.entities.CervezaEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface CervezaDao {
    @Query("SELECT * FROM cervezas ORDER BY cervezaId DESC")
    fun observeAll(): Flow<List<CervezaEntity>>

    @Query("SELECT * FROM cervezas WHERE cervezaId = :id")
    suspend fun getById(id: Int): CervezaEntity?

    @Upsert
    suspend fun upsert(entity: CervezaEntity)

    @Delete
    suspend fun delete(entity: CervezaEntity)

    @Query("DELETE FROM cervezas WHERE cervezaId = :id")
    suspend fun deleteById(id: Int)
}