package com.example.reportar.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.reportar.data.repository.IncidentRepositoryImpl
import com.example.reportar.domain.usecase.AddIncidentUseCase
import com.example.reportar.domain.usecase.DeleteIncidentUseCase
import com.example.reportar.domain.usecase.GetIncidentsUseCase
import com.example.reportar.domain.usecase.UpdateIncidentUseCase

class IncidentViewModelFactory : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(
        modelClass: Class<T>
    ): T {

        val repository = IncidentRepositoryImpl()

        val getIncidentsUseCase = GetIncidentsUseCase(repository)

        val addIncidentUseCase = AddIncidentUseCase(repository)

        val updateIncidentUseCase = UpdateIncidentUseCase(repository)

        val deleteIncidentUseCase = DeleteIncidentUseCase(repository)

        return IncidentViewModel(
            getIncidentsUseCase,
            addIncidentUseCase,
            updateIncidentUseCase,
            deleteIncidentUseCase
        ) as T
    }
}