package com.example.reportar.presentation.viewmodel

import androidx.lifecycle.ViewModel
import com.example.reportar.domain.model.Incident
import com.example.reportar.domain.usecase.AddIncidentUseCase
import com.example.reportar.domain.usecase.DeleteIncidentUseCase
import com.example.reportar.domain.usecase.GetIncidentsUseCase
import com.example.reportar.domain.usecase.UpdateIncidentUseCase
import com.example.reportar.presentation.state.IncidentState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class IncidentViewModel(

    private val getIncidentsUseCase: GetIncidentsUseCase,
    private val addIncidentUseCase: AddIncidentUseCase,
    private val updateIncidentUseCase: UpdateIncidentUseCase,
    private val deleteIncidentUseCase: DeleteIncidentUseCase

) : ViewModel() {

    private val _state = MutableStateFlow(IncidentState())

    val state: StateFlow<IncidentState> = _state

    init {
        loadIncidents()
    }

    fun loadIncidents() {

        _state.value = _state.value.copy(
            incidents = getIncidentsUseCase()
        )
    }

    fun selectIncident(incident: Incident) {

        _state.value = _state.value.copy(
            selectedIncident = incident,
            images = incident.imageUris,
            tags = incident.tags,
            latitude = incident.latitude,
            longitude = incident.longitude
        )
    }
    fun clearSelectedIncident() {

        _state.value = _state.value.copy(
            selectedIncident = null,
            images = emptyList(),
            tags = emptyList(),
            latitude = null,
            longitude = null
        )
    }

    fun addIncident(incident: Incident) {

        addIncidentUseCase(incident)

        loadIncidents()
    }

    fun updateIncident(incident: Incident) {

        updateIncidentUseCase(incident)

        loadIncidents()
    }

    fun deleteIncident(id: Int) {

        deleteIncidentUseCase(id)

        loadIncidents()
    }

    fun addImage(uri: String) {

        val updatedImages =
            _state.value.images + uri

        _state.value = _state.value.copy(
            images = updatedImages,
            currentImageIndex = updatedImages.lastIndex
        )
    }

    fun removeImage(uri: String) {

        _state.value = _state.value.copy(
            images = _state.value.images - uri
        )
    }

    fun setCurrentPhotoUri(uri: String) {

        _state.value = _state.value.copy(
            currentPhotoUri = uri
        )
    }

    fun setLocation(
        latitude: Double,
        longitude: Double
    ) {

        _state.value = _state.value.copy(
            latitude = latitude,
            longitude = longitude
        )
    }

    fun toggleTag(tag: String) {

        val currentTags =
            _state.value.tags

        _state.value =
            _state.value.copy(

                tags =
                    if (tag in currentTags) {

                        currentTags - tag

                    } else {

                        currentTags + tag
                    }
            )
    }
}