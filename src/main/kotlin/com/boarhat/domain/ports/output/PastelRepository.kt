package com.boarhat.domain.ports.output

import com.boarhat.domain.models.Pastel

interface PastelRepository {
    suspend fun getAll(): List<Pastel>
    suspend fun getById(id: Int): Pastel?
    suspend fun create(pastel: Pastel): Pastel
    suspend fun update(id: Int, pastel: Pastel): Boolean
    suspend fun delete(id: Int): Boolean
}