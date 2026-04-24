package com.boarhat.domain.ports.output

import com.boarhat.domain.models.Pedido

interface PedidoRepository {
    suspend fun getAll(): List<Pedido>
    suspend fun getById(id: Int): Pedido?
    suspend fun create(pedido: Pedido): Pedido
    suspend fun updateEstado(id: Int, estado: String): Boolean
}