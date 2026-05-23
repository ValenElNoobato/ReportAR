package com.example.reportar.domain.usecase

import com.example.reportar.domain.repository.SessionRepository

class LoginUseCase(
    private val repository: SessionRepository
) {

    fun execute(user: String, password: String): Boolean {

        return repository.login(user, password)
    }
}