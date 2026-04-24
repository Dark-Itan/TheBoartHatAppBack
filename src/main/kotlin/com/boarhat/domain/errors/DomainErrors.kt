package com.boarhat.domain.errors

sealed class DomainError(message: String) : Throwable(message) {
    object UserNotFound : DomainError("Usuario no encontrado")
    object InvalidCredentials : DomainError("Credenciales inválidas")
    object EmailAlreadyExists : DomainError("El email ya está registrado")
    object PastelNotFound : DomainError("Pastel no encontrado")
    object PedidoNotFound : DomainError("Pedido no encontrado")
    object StockInsuficiente : DomainError("Stock insuficiente")
    data class ValidationError(val errors: List<String>) : DomainError(errors.joinToString())
}