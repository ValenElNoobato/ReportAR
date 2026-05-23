package com.example.reportar.domain.usecase

import com.example.reportar.domain.repository.SessionRepository

class GetCurrentUserUseCase(
    private val repository: SessionRepository
) {

    fun execute(): String? {

        return repository.getCurrentUser()
    }
}