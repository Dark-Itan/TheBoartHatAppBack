package com.boarhat.infrastructure.plugins

import com.boarhat.domain.errors.DomainError
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.plugins.statuspages.*
import io.ktor.server.response.*

fun Application.configureStatusPages() {
    install(StatusPages) {
        exception<DomainError.UserNotFound> { call, _ ->
            call.respond(HttpStatusCode.NotFound, mapOf("error" to "Usuario no encontrado"))
        }
        exception<DomainError.InvalidCredentials> { call, _ ->
            call.respond(HttpStatusCode.Unauthorized, mapOf("error" to "Credenciales inválidas"))
        }
        exception<DomainError.EmailAlreadyExists> { call, _ ->
            call.respond(HttpStatusCode.Conflict, mapOf("error" to "Email ya registrado"))
        }
        exception<Throwable> { call, cause ->
            call.respond(HttpStatusCode.InternalServerError, mapOf("error" to (cause.message ?: "Error interno")))
        }
    }
}