package com.boarhat.application.usecases.pasteles

import com.boarhat.domain.models.Pastel
import com.boarhat.domain.ports.output.PastelRepository

class CreatePastelUseCase(
    private val pastelRepository: PastelRepository
) {
    suspend operator fun invoke(pastel: Pastel): Pastel = pastelRepository.create(pastel)
}