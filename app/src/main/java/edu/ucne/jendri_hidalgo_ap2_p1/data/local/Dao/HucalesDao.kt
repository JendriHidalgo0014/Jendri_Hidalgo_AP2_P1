package edu.ucne.jendri_hidalgo_ap2_p1.data.local.Dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import edu.ucne.jendri_hidalgo_ap2_p1.data.local.Entities.HuacalesEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface HucalesDao {
    @Query("SELECT * FROM EntradaHuacales ORDER BY EntradaId DESC")
    fun observeAll(): Flow<List<HuacalesEntity>>

    @Query("SELECT * FROM EntradaHuacales WHERE EntradaId = :id")
    suspend fun getById(id: Int): HuacalesEntity?

    @Upsert
    suspend fun upsert(entity: HuacalesEntity)

    @Delete
    suspend fun delete(entity: HuacalesEntity)

    @Query("DELETE FROM EntradaHuacales WHERE EntradaId = :id")
    suspend fun deleteById(id:Int)

    @Query("SELECT COUNT(*) > 0 FROM EntradaHuacales WHERE nombrecliente = :nombrecliente")
    suspend fun existePorNombre(nombrecliente: String): Boolean
}