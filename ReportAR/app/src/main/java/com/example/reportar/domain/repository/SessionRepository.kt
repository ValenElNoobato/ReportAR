package com.example.reportar.domain.repository

interface SessionRepository {

    fun login(user: String, password: String): Boolean

    fun logout()

    fun getCurrentUser(): String?
}