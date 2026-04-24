package com.boarhat.application.usecases.auth

import com.boarhat.domain.models.AuthResponse
import com.boarhat.domain.models.Usuario
import com.boarhat.domain.ports.output.UsuarioRepository
import com.boarhat.infrastructure.security.JWTManager
import com.boarhat.infrastructure.security.PasswordHasher

class LoginUseCase(
    private val usuarioRepository: UsuarioRepository,
    private val jwtManager: JWTManager
) {
    suspend operator fun invoke(email: String, password: String): AuthResponse? {
        val usuario = usuarioRepository.findByEmail(email) ?: return null
        if (!PasswordHasher.verify(password, usuario.passwordHash)) return null
        val token = jwtManager.generate(usuario.copy(passwordHash = ""))
        return AuthResponse(token, usuario.copy(passwordHash = ""))
    }
}