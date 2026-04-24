package com.boarhat.infrastructure.persistence.repositories

import com.boarhat.domain.models.Pastel
import com.boarhat.domain.ports.output.PastelRepository
import com.boarhat.infrastructure.persistence.dbQuery
import com.boarhat.infrastructure.persistence.tables.PastelesTable
import kotlinx.serialization.json.Json
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq

class PostgresPastelRepository : PastelRepository {

    override suspend fun getAll(): List<Pastel> = dbQuery {
        PastelesTable.selectAll()
            .orderBy(PastelesTable.nombre, SortOrder.ASC)
            .map { rowToPastel(it) }
    }

    override suspend fun getById(id: Int): Pastel? = dbQuery {
        PastelesTable.selectAll()
            .where { PastelesTable.id eq id }
            .map { rowToPastel(it) }
            .singleOrNull()
    }

    override suspend fun create(pastel: Pastel): Pastel = dbQuery {
        val stmt = PastelesTable.insert {
            it[nombre] = pastel.nombre
            it[descripcion] = pastel.descripcion
            it[precio] = pastel.precio.toBigDecimal()
            it[stock] = pastel.stock
            it[imagenUrl] = pastel.imagenUrl
            it[categoria] = pastel.categoria
            it[ingredientesJson] = Json.encodeToString(pastel.ingredientes)
            it[disponible] = pastel.disponible
        }
        pastel.copy(id = stmt[PastelesTable.id])
    }

    override suspend fun update(id: Int, pastel: Pastel): Boolean = dbQuery {
        PastelesTable.update(where = { PastelesTable.id eq id }) {
            it[nombre] = pastel.nombre
            it[descripcion] = pastel.descripcion
            it[precio] = pastel.precio.toBigDecimal()
            it[stock] = pastel.stock
            it[imagenUrl] = pastel.imagenUrl
            it[categoria] = pastel.categoria
            it[ingredientesJson] = Json.encodeToString(pastel.ingredientes)
            it[disponible] = pastel.disponible
        } > 0
    }

    override suspend fun delete(id: Int): Boolean = dbQuery {
        PastelesTable.deleteWhere { PastelesTable.id eq id } > 0
    }

    private fun rowToPastel(row: ResultRow): Pastel {
        val json = row[PastelesTable.ingredientesJson]
        val ingredientes = if (json.isNotBlank()) {
            Json.decodeFromString<List<String>>(json)
        } else emptyList()

        return Pastel(
            id = row[PastelesTable.id],
            nombre = row[PastelesTable.nombre],
            descripcion = row[PastelesTable.descripcion] ?: "",
            precio = row[PastelesTable.precio].toDouble(),
            stock = row[PastelesTable.stock],
            imagenUrl = row[PastelesTable.imagenUrl] ?: "",
            categoria = row[PastelesTable.categoria],
            ingredientes = ingredientes,
            disponible = row[PastelesTable.disponible]
        )
    }
}