package com.boarhat.infrastructure.controllers

import com.boarhat.application.usecases.auth.*
import com.boarhat.domain.models.*
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.authController(
    loginUseCase: LoginUseCase,
    registerUseCase: RegisterUseCase
) {
    post("/auth/login") {
        val request = call.receive<LoginRequest>()
        val result = loginUseCase(request.email, request.password)
        if (result != null) {
            call.respond(HttpStatusCode.OK, result)
        } else {
            call.respond(HttpStatusCode.Unauthorized, mapOf("error" to "Credenciales inválidas"))
        }
    }

    post("/auth/register") {
        val request = call.receive<RegisterRequest>()
        val result = registerUseCase(request)
        if (result != null) {
            call.respond(HttpStatusCode.Created, result)
        } else {
            call.respond(HttpStatusCode.Conflict, mapOf("error" to "Email ya registrado"))
        }
    }
}