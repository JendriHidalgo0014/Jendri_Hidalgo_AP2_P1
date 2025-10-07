package edu.ucne.jendri_hidalgo_ap2_p1.data.local.Entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date


@Entity(tableName = "EntradaHuacales")
data class HuacalesEntity(
    @PrimaryKey(autoGenerate = true)
    val entradaId: Int? = null,
    val fecha: Date,
    val nombrecliente: String = "",
    val cantidad: Int = 0,
    val precio: Int = 0
)