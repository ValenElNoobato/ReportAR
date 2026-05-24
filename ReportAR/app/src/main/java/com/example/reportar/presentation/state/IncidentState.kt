package com.example.reportar.presentation.state

import com.example.reportar.domain.model.Incident

data class IncidentState(

    val incidents: List<Incident> = emptyList(),

    val selectedIncident: Incident? = null
)