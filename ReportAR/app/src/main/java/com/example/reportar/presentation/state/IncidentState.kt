package com.example.reportar.presentation.state

import com.example.reportar.domain.model.Incident

data class IncidentState(

    val incidents: List<Incident> = emptyList(),

    val selectedIncident: Incident? = null,

    val images: List<String> = emptyList(),

    val tags: List<String> = emptyList(),

    val currentPhotoUri: String? = null,

    val currentImageIndex: Int = 0,

    val latitude: Double? = null,

    val longitude: Double? = null
)