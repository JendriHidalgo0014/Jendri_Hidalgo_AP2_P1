package edu.ucne.jendri_hidalgo_ap2_p1.data.local.mapper

import edu.ucne.jendri_hidalgo_ap2_p1.domain.model.EntradaHucales
import edu.ucne.jendri_hidalgo_ap2_p1.data.local.Entities.HuacalesEntity

fun HuacalesEntity.toDomain(): EntradaHucales {
    return EntradaHucales(
        entradaId = entradaId ?: 0,
        fecha = fecha,
        nombrecliente = nombrecliente,
        cantidad = cantidad,
        precio = precio
    )
}

fun EntradaHucales.toEntity(): HuacalesEntity {
    return HuacalesEntity(
        entradaId = if (entradaId == 0) null else entradaId,
        fecha = fecha,
        nombrecliente = nombrecliente,
        cantidad = cantidad,
        precio = precio
    )
}