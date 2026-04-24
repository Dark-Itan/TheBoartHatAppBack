package com.boarhat.application.usecases.pasteles

import com.boarhat.domain.ports.output.PastelRepository

class DeletePastelUseCase(
    private val pastelRepository: PastelRepository
) {
    suspend operator fun invoke(id: Int): Boolean = pastelRepository.delete(id)
}