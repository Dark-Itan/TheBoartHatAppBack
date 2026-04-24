package com.boarhat.infrastructure.persistence.tables

import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.kotlin.datetime.datetime
import kotlinx.datetime.*

object UsuariosTable : Table("usuarios") {
    val id = integer("id").autoIncrement()
    val nombre = varchar("nombre", 100)
    val email = varchar("email", 100).uniqueIndex()
    val passwordHash = varchar("password_hash", 255)
    val rol = varchar("rol", 20).check { it inList listOf("cliente", "vendedor", "admin") }
    val telefono =  varchar("telefono", 20).nullable()
    val createdAt = datetime("created_at").default(Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()))
    override val primaryKey = PrimaryKey(id)
}

object PastelesTable : Table("pasteles") {
    val id = integer("id").autoIncrement()
    val nombre = varchar("nombre", 100)
    val descripcion = text("descripcion")
    val precio = decimal("precio", 10, 2)
    val stock = integer("stock").default(0)
    val imagenUrl = varchar("imagen_url", 500)
    val categoria = varchar("categoria", 50)
    val ingredientesJson = text("ingredientes_json").default("[]")
    val disponible = bool("disponible").default(true)
    val createdAt = datetime("created_at").default(Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()))
    override val primaryKey = PrimaryKey(id)
}

object PedidosTable : Table("pedidos") {
    val id = integer("id").autoIncrement()
    val clienteNombre = varchar("cliente_nombre", 100)
    val clienteTelefono = varchar("cliente_telefono", 20)
    val total = decimal("total", 10, 2)
    val anticipo50 = decimal("anticipo50", 10, 2)
    val fechaRecoleccion = varchar("fecha_recoleccion", 50)
    val estado = varchar("estado", 20).check { it inList listOf("PENDIENTE", "PAGADO", "PREPARANDO", "ENTREGADO", "CANCELADO") }
    val metodoPago = varchar("metodo_pago", 20).check { it inList listOf("EFECTIVO", "TARJETA", "TRANSFERENCIA") }
    val comprobanteUrl = varchar("comprobante_url", 500).nullable()
    val createdAt = datetime("created_at").default(Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()))
    override val primaryKey = PrimaryKey(id)
}

object ItemsPedidoTable : Table("items_pedido") {
    val id = integer("id").autoIncrement()
    val pedidoId = integer("pedido_id").references(PedidosTable.id, onDelete = ReferenceOption.CASCADE)
    val pastelId = integer("pastel_id")
    val nombrePastel = varchar("nombre_pastel", 100)
    val cantidad = integer("cantidad")
    val precioUnitario = decimal("precio_unitario", 10, 2)
    val subtotal = decimal("subtotal", 10, 2)
    val detallesAdicionales = text("detalles_adicionales").nullable()
    override val primaryKey = PrimaryKey(id)
}