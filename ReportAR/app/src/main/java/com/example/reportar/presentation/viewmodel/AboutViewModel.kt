package com.example.reportar.presentation.viewmodel

import androidx.lifecycle.ViewModel
import com.example.reportar.presentation.state.AboutState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class AboutViewModel : ViewModel() {

    private val _state = MutableStateFlow(AboutState())

    val state: StateFlow<AboutState> = _state
}