package com.boarhat.infrastructure.plugins

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.plugins.cors.routing.*

fun Application.configureCORS() {
    install(CORS) {
        allowHost("0.0.0.0:8080")
        allowHost("localhost:8080")
        allowHost("10.0.2.2:8080")
        allowHost("127.0.0.1:8080")
        allowCredentials = true
        allowNonSimpleContentTypes = true
        anyHost()
        allowMethod(HttpMethod.Get)
        allowMethod(HttpMethod.Post)
        allowMethod(HttpMethod.Put)
        allowMethod(HttpMethod.Delete)
        allowHeader(HttpHeaders.Authorization)
        allowHeader(HttpHeaders.ContentType)
    }
}