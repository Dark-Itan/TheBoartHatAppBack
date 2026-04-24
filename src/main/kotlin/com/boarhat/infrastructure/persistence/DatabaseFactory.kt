package com.boarhat.infrastructure.persistence

import com.boarhat.infrastructure.persistence.tables.*
import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import io.ktor.server.application.*
import kotlinx.coroutines.Dispatchers
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import org.jetbrains.exposed.sql.transactions.transaction

fun Application.configureDatabase() {
    val config = HikariConfig().apply {
        jdbcUrl = "jdbc:postgresql://localhost:5432/boarhat_db"
        driverClassName = "org.postgresql.Driver"
        username = "postgres"
        password = "gomaru"
        maximumPoolSize = 10
    }

    val dataSource = HikariDataSource(config)
    Database.connect(dataSource)

    transaction {
        SchemaUtils.createMissingTablesAndColumns(
            UsuariosTable,
            PastelesTable,
            PedidosTable,
            ItemsPedidoTable
        )
    }
}

suspend fun <T> dbQuery(block: suspend org.jetbrains.exposed.sql.Transaction.() -> T): T =
    newSuspendedTransaction(Dispatchers.IO, statement = block)