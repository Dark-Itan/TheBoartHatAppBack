package com.boarhat.infrastructure.persistence.repositories

import com.boarhat.domain.models.*
import com.boarhat.domain.ports.output.PedidoRepository
import com.boarhat.infrastructure.persistence.dbQuery
import com.boarhat.infrastructure.persistence.tables.*
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq

class PostgresPedidoRepository : PedidoRepository {

    override suspend fun getAll(): List<Pedido> = dbQuery {
        PedidosTable.selectAll()
            .orderBy(PedidosTable.createdAt, SortOrder.DESC)
            .map { rowToPedido(it) }
    }

    override suspend fun getById(id: Int): Pedido? = dbQuery {
        PedidosTable.selectAll()
            .where { PedidosTable.id eq id }
            .map { rowToPedido(it) }
            .singleOrNull()
    }

    override suspend fun create(pedido: Pedido): Pedido = dbQuery {
        val stmt = PedidosTable.insert {
            it[clienteNombre] = pedido.clienteNombre
            it[clienteTelefono] = pedido.clienteTelefono
            it[total] = pedido.total.toBigDecimal()
            it[anticipo50] = pedido.anticipo50.toBigDecimal()
            it[fechaRecoleccion] = pedido.fechaRecoleccion
            it[estado] = pedido.estado.name
            it[metodoPago] = pedido.metodoPago.name
            it[comprobanteUrl] = pedido.comprobanteUrl
        }
        val pedidoId = stmt[PedidosTable.id]

        pedido.items.forEach { item ->
            ItemsPedidoTable.insert {
                it[ItemsPedidoTable.pedidoId] = pedidoId
                it[ItemsPedidoTable.pastelId] = item.pastelId
                it[ItemsPedidoTable.nombrePastel] = item.nombre
                it[ItemsPedidoTable.cantidad] = item.cantidad
                it[ItemsPedidoTable.precioUnitario] = item.precioUnitario.toBigDecimal()
                it[ItemsPedidoTable.subtotal] = item.subtotal.toBigDecimal()
                it[ItemsPedidoTable.detallesAdicionales] = item.detallesAdicionales
            }

            PastelesTable.update(where = { PastelesTable.id eq item.pastelId }) {
                with(SqlExpressionBuilder) {
                    it[stock] = stock - item.cantidad
                }
            }
        }

        pedido.copy(id = pedidoId)
    }

    override suspend fun updateEstado(id: Int, estado: String): Boolean = dbQuery {
        PedidosTable.update(where = { PedidosTable.id eq id }) {
            it[PedidosTable.estado] = estado
        } > 0
    }

    private suspend fun rowToPedido(row: ResultRow): Pedido {
        val pedidoId = row[PedidosTable.id]
        val items = dbQuery {
            ItemsPedidoTable.selectAll()
                .where { ItemsPedidoTable.pedidoId eq pedidoId }
                .map {
                    ItemPedido(
                        pastelId = it[ItemsPedidoTable.pastelId],
                        nombre = it[ItemsPedidoTable.nombrePastel],
                        cantidad = it[ItemsPedidoTable.cantidad],
                        precioUnitario = it[ItemsPedidoTable.precioUnitario].toDouble(),
                        subtotal = it[ItemsPedidoTable.subtotal].toDouble(),
                        detallesAdicionales = it[ItemsPedidoTable.detallesAdicionales] ?: ""
                    )
                }
        }

        return Pedido(
            id = pedidoId,
            clienteNombre = row[PedidosTable.clienteNombre],
            clienteTelefono = row[PedidosTable.clienteTelefono],
            items = items,
            total = row[PedidosTable.total].toDouble(),
            anticipo50 = row[PedidosTable.anticipo50].toDouble(),
            fecha = row[PedidosTable.createdAt].toString(),
            fechaRecoleccion = row[PedidosTable.fechaRecoleccion],
            estado = EstadoPedido.valueOf(row[PedidosTable.estado]),
            metodoPago = MetodoPago.valueOf(row[PedidosTable.metodoPago]),
            comprobanteUrl = row[PedidosTable.comprobanteUrl]
        )
    }
}