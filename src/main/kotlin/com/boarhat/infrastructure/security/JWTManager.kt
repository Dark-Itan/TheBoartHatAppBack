package com.boarhat.infrastructure.security

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.boarhat.domain.models.Usuario
import java.util.*

class JWTManager {
    private val secret = "boarhat-super-secret-key-change-in-production"
    private val issuer = "boarhat-api"
    private val expiration = 7 * 24 * 60 * 60 * 1000L // 7 días

    fun generate(usuario: Usuario): String = JWT.create()
        .withIssuer(issuer)
        .withClaim("id", usuario.id)
        .withClaim("email", usuario.email)
        .withClaim("rol", usuario.rol)
        .withExpiresAt(Date(System.currentTimeMillis() + expiration))
        .sign(Algorithm.HMAC256(secret))

    fun verify(token: String): Boolean = try {
        JWT.require(Algorithm.HMAC256(secret))
            .withIssuer(issuer)
            .build()
            .verify(token)
        true
    } catch (e: Exception) {
        false
    }

    fun getUserId(token: String): Int? = try {
        JWT.decode(token).getClaim("id").asInt()
    } catch (e: Exception) {
        null
    }
}