package com.boarhat.infrastructure.plugins

import com.boarhat.infrastructure.controllers.*
import io.ktor.server.application.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject

fun Application.configureRouting() {
    routing {
        val loginUseCase by inject<com.boarhat.application.usecases.auth.LoginUseCase>()
        val registerUseCase by inject<com.boarhat.application.usecases.auth.RegisterUseCase>()
        val getPastelesUseCase by inject<com.boarhat.application.usecases.pasteles.GetPastelesUseCase>()
        val createPastelUseCase by inject<com.boarhat.application.usecases.pasteles.CreatePastelUseCase>()
        val updatePastelUseCase by inject<com.boarhat.application.usecases.pasteles.UpdatePastelUseCase>()
        val deletePastelUseCase by inject<com.boarhat.application.usecases.pasteles.DeletePastelUseCase>()
        val createPedidoUseCase by inject<com.boarhat.application.usecases.pedidos.CreatePedidoUseCase>()
        val getPedidosUseCase by inject<com.boarhat.application.usecases.pedidos.GetPedidosUseCase>()
        val updateEstadoPedidoUseCase by inject<com.boarhat.application.usecases.pedidos.UpdateEstadoPedidoUseCase>()

        authController(loginUseCase, registerUseCase)
        pastelController(getPastelesUseCase, createPastelUseCase, updatePastelUseCase, deletePastelUseCase)
        pedidoController(createPedidoUseCase, getPedidosUseCase, updateEstadoPedidoUseCase)
        homeController()
    }
}