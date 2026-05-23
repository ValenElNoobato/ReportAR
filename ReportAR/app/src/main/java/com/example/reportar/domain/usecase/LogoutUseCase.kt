package com.example.reportar.domain.usecase

import com.example.reportar.domain.repository.SessionRepository

class LogoutUseCase(
    private val repository: SessionRepository
) {

    fun execute() {

        repository.logout()
    }
}