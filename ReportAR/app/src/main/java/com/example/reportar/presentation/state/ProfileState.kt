package com.example.reportar.presentation.state

data class ProfileState (
    val username: String = "",
    val isLogged: Boolean = false,
    val error: String? = null
)