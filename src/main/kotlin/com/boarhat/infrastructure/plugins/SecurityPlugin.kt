package com.boarhat.infrastructure.plugins

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*

fun Application.configureSecurity() {
    val secret = "boarhat-super-secret-key-change-in-production"
    val issuer = "boarhat-api"

    install(Authentication) {
        jwt("auth-jwt") {
            realm = "BoarHat API"
            verifier(
                JWT.require(Algorithm.HMAC256(secret))
                    .withIssuer(issuer)
                    .build()
            )
            validate { credential ->
                if (credential.payload.getClaim("id").asInt() != null) {
                    JWTPrincipal(credential.payload)
                } else null
            }
        }
    }
}