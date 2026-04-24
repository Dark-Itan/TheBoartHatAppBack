package com.boarhat.infrastructure.controllers

import com.boarhat.application.usecases.pasteles.*
import com.boarhat.domain.models.Pastel
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.pastelController(
    getPastelesUseCase: GetPastelesUseCase,
    createPastelUseCase: CreatePastelUseCase,
    updatePastelUseCase: UpdatePastelUseCase,
    deletePastelUseCase: DeletePastelUseCase
) {
    get("/pasteles") {
        val pasteles = getPastelesUseCase()
        call.respond(HttpStatusCode.OK, pasteles)
    }

    post("/pasteles") {
        val pastel = call.receive<Pastel>()
        val created = createPastelUseCase(pastel)
        call.respond(HttpStatusCode.Created, created)
    }

    put("/pasteles/{id}") {
        val id = call.parameters["id"]?.toIntOrNull() ?: return@put call.respond(HttpStatusCode.BadRequest)
        val pastel = call.receive<Pastel>()
        val updated = updatePastelUseCase(id, pastel)
        if (updated) {
            call.respond(HttpStatusCode.OK, mapOf("success" to true))
        } else {
            call.respond(HttpStatusCode.NotFound)
        }
    }

    delete("/pasteles/{id}") {
        val id = call.parameters["id"]?.toIntOrNull() ?: return@delete call.respond(HttpStatusCode.BadRequest)
        val deleted = deletePastelUseCase(id)
        if (deleted) {
            call.respond(HttpStatusCode.OK, mapOf("success" to true))
        } else {
            call.respond(HttpStatusCode.NotFound)
        }
    }
}