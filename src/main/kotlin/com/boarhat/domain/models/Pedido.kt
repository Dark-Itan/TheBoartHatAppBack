package com.boarhat.domain.models

import kotlinx.serialization.Serializable

@Serializable
data class Pedido(
    val id: Int = 0,
    val clienteNombre: String,
    val clienteTelefono: String,
    val items: List<ItemPedido>,
    val total: Double,
    val anticipo50: Double = total / 2,
    val fecha: String = "",
    val fechaRecoleccion: String,
    val estado: EstadoPedido = EstadoPedido.PENDIENTE,
    val metodoPago: MetodoPago = MetodoPago.TRANSFERENCIA,
    val comprobanteUrl: String? = null
)

@Serializable
data class ItemPedido(
    val pastelId: Int,
    val nombre: String,
    val cantidad: Int,
    val precioUnitario: Double,
    val subtotal: Double,
    val detallesAdicionales: String = ""
)

@Serializable
enum class EstadoPedido { PENDIENTE, PAGADO, PREPARANDO, ENTREGADO, CANCELADO }

@Serializable
enum class MetodoPago { EFECTIVO, TARJETA, TRANSFERENCIA }