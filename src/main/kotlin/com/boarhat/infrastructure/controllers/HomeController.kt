package com.boarhat.infrastructure.controllers

import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.serialization.Serializable

@Serializable
data class HomeResponse(
    val mensaje: String,
    val endpoints: Map<String, String>
)

fun Route.homeController() {
    get("/") {
        call.respond(
            HomeResponse(
                mensaje = "The Boar Hat API - ¡Corriendo!",
                endpoints = mapOf(
                    "POST /auth/register" to "Registrar nuevo usuario",
                    "POST /auth/login" to "Iniciar sesión",
                    "GET /pasteles" to "Lista de pasteles",
                    "GET /pedidos" to "Lista de pedidos"
                )
            )
        )
    }
}