package com.example.reportar.domain.model

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.DirectionsCar
import androidx.compose.material.icons.filled.DirectionsWalk
import androidx.compose.material.icons.filled.Lightbulb
import androidx.compose.material.icons.filled.Park
import androidx.compose.material.icons.filled.Report
import androidx.compose.material.icons.filled.Traffic
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material.icons.filled.WaterDrop

object IncidentTags {

    val availableTags = listOf(

        IncidentTag(
            id = "bache",
            name = "Bache",
            icon = Icons.Default.Warning
        ),

        IncidentTag(
            id = "luminaria",
            name = "Luminaria",
            icon = Icons.Default.Lightbulb
        ),

        IncidentTag(
            id = "basura",
            name = "Basura",
            icon = Icons.Default.Delete
        ),

        IncidentTag(
            id = "semaforo",
            name = "Semáforo",
            icon = Icons.Default.Traffic
        ),

        IncidentTag(
            id = "vereda",
            name = "Vereda",
            icon = Icons.Default.DirectionsWalk
        ),

        IncidentTag(
            id = "vandalismo",
            name = "Vandalismo",
            icon = Icons.Default.Report
        ),

        IncidentTag(
            id = "transito",
            name = "Tránsito",
            icon = Icons.Default.DirectionsCar
        ),

        IncidentTag(
            id = "alcantarilla",
            name = "Alcantarilla",
            icon = Icons.Default.WaterDrop
        ),

        IncidentTag(
            id = "espacio_verde",
            name = "Espacio Verde",
            icon = Icons.Default.Park
        )
    )
}