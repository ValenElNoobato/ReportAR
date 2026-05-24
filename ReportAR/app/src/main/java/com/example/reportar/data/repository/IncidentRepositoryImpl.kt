package com.example.reportar.data.repository

import com.example.reportar.domain.model.Incident
import com.example.reportar.domain.repository.IncidentRepository

class IncidentRepositoryImpl : IncidentRepository {

    private val incidents = mutableListOf<Incident>()

    override fun getIncidents(): List<Incident> {

        return incidents
    }

    override fun addIncident(incident: Incident) {

        incidents.add(incident)
    }

    override fun updateIncident(incident: Incident) {

        val index = incidents.indexOfFirst {
            it.id == incident.id
        }

        if (index != -1) {

            incidents[index] = incident
        }
    }

    override fun deleteIncident(id: Int) {

        incidents.removeAll {
            it.id == id
        }
    }
}