package com.boarhat.application.usecases.auth

import com.boarhat.domain.models.AuthResponse
import com.boarhat.domain.models.RegisterRequest
import com.boarhat.domain.models.Usuario
import com.boarhat.domain.ports.output.UsuarioRepository
import com.boarhat.infrastructure.security.JWTManager
import com.boarhat.infrastructure.security.PasswordHasher

class RegisterUseCase(
    private val usuarioRepository: UsuarioRepository,
    private val jwtManager: JWTManager
) {
    suspend operator fun invoke(request: RegisterRequest): AuthResponse? {
        if (usuarioRepository.findByEmail(request.email) != null) return null
        val usuario = Usuario(
            nombre = request.nombre,
            email = request.email,
            passwordHash = PasswordHasher.hash(request.password),
            rol = "cliente",
            telefono = ""
        )
        val created = usuarioRepository.create(usuario)
        val token = jwtManager.generate(created.copy(passwordHash = ""))
        return AuthResponse(token, created.copy(passwordHash = ""))
    }
}