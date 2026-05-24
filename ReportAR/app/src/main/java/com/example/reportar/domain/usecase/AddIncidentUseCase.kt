package com.example.reportar.domain.usecase

import com.example.reportar.domain.model.Incident
import com.example.reportar.domain.repository.IncidentRepository

class AddIncidentUseCase(
    private val repository: IncidentRepository
) {

    operator fun invoke(incident: Incident) {

        repository.addIncident(incident)
    }
}