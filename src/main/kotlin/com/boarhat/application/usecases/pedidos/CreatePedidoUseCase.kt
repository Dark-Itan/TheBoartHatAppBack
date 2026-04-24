package com.boarhat.application.usecases.pedidos

import com.boarhat.domain.models.Pedido
import com.boarhat.domain.ports.output.PedidoRepository

class CreatePedidoUseCase(
    private val pedidoRepository: PedidoRepository
) {
    suspend operator fun invoke(pedido: Pedido): Pedido = pedidoRepository.create(pedido)
}