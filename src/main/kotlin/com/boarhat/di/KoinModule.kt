package com.boarhat.di

import com.boarhat.application.usecases.auth.*
import com.boarhat.application.usecases.pasteles.*
import com.boarhat.application.usecases.pedidos.*
import com.boarhat.domain.ports.output.*
import com.boarhat.infrastructure.persistence.repositories.*
import com.boarhat.infrastructure.security.*
import org.koin.dsl.module

val koinModule = module {
    // Security
    single { JWTManager() }
    single { PasswordHasher }

    // Repositories
    single<UsuarioRepository> { PostgresUsuarioRepository() }
    single<PastelRepository> { PostgresPastelRepository() }
    single<PedidoRepository> { PostgresPedidoRepository() }

    // Auth UseCases
    single { LoginUseCase(get(), get()) }
    single { RegisterUseCase(get(), get()) }

    // Pasteles UseCases
    single { GetPastelesUseCase(get()) }
    single { CreatePastelUseCase(get()) }
    single { UpdatePastelUseCase(get()) }
    single { DeletePastelUseCase(get()) }

    // Pedidos UseCases
    single { CreatePedidoUseCase(get()) }
    single { GetPedidosUseCase(get()) }
    single { UpdateEstadoPedidoUseCase(get()) }
}
