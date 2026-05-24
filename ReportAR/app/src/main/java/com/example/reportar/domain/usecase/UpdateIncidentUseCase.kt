package com.example.reportar.domain.usecase

import com.example.reportar.domain.model.Incident
import com.example.reportar.domain.repository.IncidentRepository

class UpdateIncidentUseCase(
    private val repository: IncidentRepository
) {

    operator fun invoke(incident: Incident) {

        repository.updateIncident(incident)
    }
}