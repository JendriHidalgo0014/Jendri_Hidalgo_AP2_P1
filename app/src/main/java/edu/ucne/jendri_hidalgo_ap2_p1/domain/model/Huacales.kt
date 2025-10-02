package edu.ucne.jendri_hidalgo_ap2_p1.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class EntradaHucales(
    val entradaId: Int = 0,
    val fecha: String,
    val nombrecliente: String,
    val cantidad: Int,
    val precio: Int
)
