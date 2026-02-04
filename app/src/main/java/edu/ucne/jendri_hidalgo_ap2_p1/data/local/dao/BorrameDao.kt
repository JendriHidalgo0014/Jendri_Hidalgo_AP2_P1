package edu.ucne.jendri_hidalgo_ap2_p1.data.local.dao

import androidx.room.Dao
import androidx.room.Upsert
import edu.ucne.jendri_hidalgo_ap2_p1.data.local.entities.BorrameEntity

@Dao
interface BorrameDao {
    @Upsert
    suspend fun upsert(entity: BorrameEntity)
}