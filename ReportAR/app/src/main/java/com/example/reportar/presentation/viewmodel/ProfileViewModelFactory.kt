package com.example.reportar.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.reportar.data.repository.SessionRepositoryImpl
import com.example.reportar.domain.usecase.GetCurrentUserUseCase
import com.example.reportar.domain.usecase.LoginUseCase
import com.example.reportar.domain.usecase.LogoutUseCase

class ProfileViewModelFactory : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {

        val repository = SessionRepositoryImpl()

        val loginUseCase = LoginUseCase(repository)

        val logoutUseCase = LogoutUseCase(repository)

        val getCurrentUserUseCase = GetCurrentUserUseCase(repository)

        return ProfileViewModel(
            repository,
            loginUseCase,
            logoutUseCase,
            getCurrentUserUseCase
        ) as T
    }
}