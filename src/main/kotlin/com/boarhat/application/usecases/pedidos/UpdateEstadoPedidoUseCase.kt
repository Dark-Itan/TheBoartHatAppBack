package com.boarhat.application.usecases.pedidos

import com.boarhat.domain.ports.output.PedidoRepository

class UpdateEstadoPedidoUseCase(
    private val pedidoRepository: PedidoRepository
) {
    suspend operator fun invoke(id: Int, estado: String): Boolean = pedidoRepository.updateEstado(id, estado)
}