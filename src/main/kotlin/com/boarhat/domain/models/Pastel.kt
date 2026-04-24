package com.boarhat.domain.models

import kotlinx.serialization.Serializable

@Serializable
data class Pastel(
    val id: Int = 0,
    val nombre: String,
    val descripcion: String,
    val precio: Double,
    val stock: Int,
    val imagenUrl: String,
    val categoria: String,
    val ingredientes: List<String>,
    val disponible: Boolean = true
)