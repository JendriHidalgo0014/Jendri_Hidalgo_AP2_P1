package edu.ucne.jendri_hidalgo_ap2_p1.data.local.mapper

import edu.ucne.jendri_hidalgo_ap2_p1.data.local.entities.CervezaEntity
import edu.ucne.jendri_hidalgo_ap2_p1.domain.model.Cervezas


fun CervezaEntity.toDomain(): Cervezas = Cervezas(
     cervezaId = cervezaId,
 nombre = nombre,
 marca = marca,
 puntuacion = puntuacion

)
fun Cervezas.toEntity(): CervezaEntity = CervezaEntity(
    cervezaId = cervezaId,
    nombre = nombre,
    marca = marca,
    puntuacion = puntuacion
)