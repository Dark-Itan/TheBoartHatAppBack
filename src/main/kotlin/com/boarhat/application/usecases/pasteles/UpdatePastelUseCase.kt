package com.boarhat.application.usecases.pasteles

import com.boarhat.domain.models.Pastel
import com.boarhat.domain.ports.output.PastelRepository

class UpdatePastelUseCase(
    private val pastelRepository: PastelRepository
) {
    suspend operator fun invoke(id: Int, pastel: Pastel): Boolean = pastelRepository.update(id, pastel)
}