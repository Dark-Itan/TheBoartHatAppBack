package com.boarhat.application.usecases.pedidos

import com.boarhat.domain.models.Pedido
import com.boarhat.domain.ports.output.PedidoRepository

class GetPedidosUseCase(
    private val pedidoRepository: PedidoRepository
) {
    suspend operator fun invoke(): List<Pedido> = pedidoRepository.getAll()
}