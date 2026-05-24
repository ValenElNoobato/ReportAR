package com.example.reportar.domain.usecase

import com.example.reportar.domain.model.Incident
import com.example.reportar.domain.repository.IncidentRepository

class GetIncidentsUseCase(
    private val repository: IncidentRepository
) {

    operator fun invoke(): List<Incident> {

        return repository.getIncidents()
    }
}