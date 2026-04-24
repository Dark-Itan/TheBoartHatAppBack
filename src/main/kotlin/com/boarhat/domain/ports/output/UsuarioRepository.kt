package com.boarhat.domain.ports.output

import com.boarhat.domain.models.Usuario

interface UsuarioRepository {
    suspend fun findByEmail(email: String): Usuario?
    suspend fun create(usuario: Usuario): Usuario
    suspend fun updatePassword(id: Int, newHash: String): Boolean
}