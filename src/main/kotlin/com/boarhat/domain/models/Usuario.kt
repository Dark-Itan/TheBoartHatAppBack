package com.boarhat.domain.models

import kotlinx.serialization.Serializable

@Serializable
data class Usuario(
    val id: Int = 0,
    val nombre: String,
    val email: String,
    val passwordHash: String = "",
    val rol: String,
    val telefono: String
)

@Serializable
data class LoginRequest(val email: String, val password: String)

@Serializable
data class RegisterRequest(
    val nombre: String,
    val email: String,
    val password: String
)

@Serializable
data class AuthResponse(val token: String, val usuario: Usuario)