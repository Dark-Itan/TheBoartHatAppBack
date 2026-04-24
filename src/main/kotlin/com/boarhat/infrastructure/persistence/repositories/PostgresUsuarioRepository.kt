package com.boarhat.infrastructure.persistence.repositories

import com.boarhat.domain.models.Usuario
import com.boarhat.domain.ports.output.UsuarioRepository
import com.boarhat.infrastructure.persistence.dbQuery
import com.boarhat.infrastructure.persistence.tables.UsuariosTable
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq

class PostgresUsuarioRepository : UsuarioRepository {

    override suspend fun findByEmail(email: String): Usuario? = dbQuery {
        UsuariosTable.selectAll()
            .where { UsuariosTable.email eq email }
            .map { rowToUsuario(it) }
            .singleOrNull()
    }

    override suspend fun create(usuario: Usuario): Usuario = dbQuery {
        val stmt = UsuariosTable.insert {
            it[nombre] = usuario.nombre
            it[email] = usuario.email
            it[passwordHash] = usuario.passwordHash
            it[rol] = usuario.rol
            it[telefono] = usuario.telefono
        }
        usuario.copy(id = stmt[UsuariosTable.id])
    }

    override suspend fun updatePassword(id: Int, newHash: String): Boolean = dbQuery {
        UsuariosTable.update(where = { UsuariosTable.id eq id }) {
            it[passwordHash] = newHash
        } > 0
    }

    private fun rowToUsuario(row: ResultRow) = Usuario(
        id = row[UsuariosTable.id],
        nombre = row[UsuariosTable.nombre],
        email = row[UsuariosTable.email],
        passwordHash = row[UsuariosTable.passwordHash],
        rol = row[UsuariosTable.rol],
        telefono = row[UsuariosTable.telefono] ?: ""
    )
}