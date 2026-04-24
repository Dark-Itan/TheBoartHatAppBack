package com.boarhat.infrastructure.controllers

import com.boarhat.application.usecases.pedidos.*
import com.boarhat.domain.models.Pedido
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.pedidoController(
    createPedidoUseCase: CreatePedidoUseCase,
    getPedidosUseCase: GetPedidosUseCase,
    updateEstadoPedidoUseCase: UpdateEstadoPedidoUseCase
) {
    get("/pedidos") {
        val pedidos = getPedidosUseCase()
        call.respond(HttpStatusCode.OK, pedidos)
    }

    post("/pedidos") {
        val pedido = call.receive<Pedido>()
        val created = createPedidoUseCase(pedido)
        call.respond(HttpStatusCode.Created, created)
    }

    put("/pedidos/{id}/estado") {
        val id = call.parameters["id"]?.toIntOrNull() ?: return@put call.respond(HttpStatusCode.BadRequest)
        val estado = call.receive<Map<String, String>>()["estado"] ?: return@put call.respond(HttpStatusCode.BadRequest)
        val updated = updateEstadoPedidoUseCase(id, estado)
        if (updated) {
            call.respond(HttpStatusCode.OK, mapOf("success" to true))
        } else {
            call.respond(HttpStatusCode.NotFound)
        }
    }
}