package com.example.reportar.domain.usecase

import com.example.reportar.domain.repository.IncidentRepository

class DeleteIncidentUseCase(
    private val repository: IncidentRepository
) {

    operator fun invoke(id: Int) {

        repository.deleteIncident(id)
    }
}