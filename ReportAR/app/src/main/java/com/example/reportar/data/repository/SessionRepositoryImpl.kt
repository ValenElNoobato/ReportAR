package com.example.reportar.data.repository

import com.example.reportar.domain.repository.SessionRepository

class SessionRepositoryImpl : SessionRepository {

    private var currentUser: String? = null

    override fun login(user: String, password: String): Boolean {

        return if (user == "admin" && password == "123456") {

            currentUser = user
            true

        } else {

            false
        }
    }

    override fun logout() {

        currentUser = null
    }

    override fun getCurrentUser(): String? {

        return currentUser
    }
}