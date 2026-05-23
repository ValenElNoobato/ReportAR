package com.example.reportar.presentation.viewmodel

import androidx.lifecycle.ViewModel
import com.example.reportar.presentation.state.HomeState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class HomeViewModel : ViewModel() {
    private val _state = MutableStateFlow(HomeState())

    val state: StateFlow<HomeState> = _state

    fun updateWelcomeMessage(message: String) {
        _state.value = _state.value.copy(
            title = message
        )
    }
}