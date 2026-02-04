package edu.ucne.jendri_hidalgo_ap2_p1.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "borrame")
data class BorrameEntity(
    @PrimaryKey(autoGenerate = true)
    val borrameId: Int = 0
)
