package com.example.reportar.domain.model

data class Incident(

    val id: Int,

    val title: String,

    val description: String,

    val latitude: Double,

    val longitude: Double,

    val tags: List<String> = emptyList(),

    val imageUris: List<String> = emptyList()
)