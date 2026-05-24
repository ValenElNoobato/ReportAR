package com.example.reportar.domain.repository

import com.example.reportar.domain.model.Incident

interface IncidentRepository {

    fun getIncidents(): List<Incident>

    fun addIncident(incident: Incident)

    fun updateIncident(incident: Incident)

    fun deleteIncident(id: Int)
}