package com.example.reportar.presentation.viewmodel

import androidx.lifecycle.ViewModel
import com.example.reportar.data.repository.SessionRepositoryImpl
import com.example.reportar.domain.usecase.GetCurrentUserUseCase
import com.example.reportar.domain.usecase.LoginUseCase
import com.example.reportar.domain.usecase.LogoutUseCase
import com.example.reportar.presentation.state.ProfileState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class ProfileViewModel(
    private val repository: SessionRepositoryImpl,
    private val loginUseCase: LoginUseCase,
    private val logoutUseCase: LogoutUseCase,
    private val getCurrentUserUseCase: GetCurrentUserUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(ProfileState())

    val state: StateFlow<ProfileState> = _state

    fun login(user: String, pass: String) {

        val success = loginUseCase.execute(user, pass)

        if (success) {

            _state.value = _state.value.copy(
                username = user,
                isLogged = true,
                error = null
            )

        } else {

            _state.value = _state.value.copy(
                error = "Credenciales incorrectas"
            )
        }
    }

    fun logout() {

        logoutUseCase.execute()

        _state.value = ProfileState()
    }
}