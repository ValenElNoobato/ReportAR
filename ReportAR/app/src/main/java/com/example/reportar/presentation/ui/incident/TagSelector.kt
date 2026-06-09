package com.example.reportar.presentation.ui.incident

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.AssistChip
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.reportar.domain.model.IncidentTag

@Composable
fun TagSelector(

    availableTags: List<IncidentTag>,

    selectedTags: List<String>,

    onTagClick: (String) -> Unit
) {

    Column {

        Text(

            text = "Categorías",

            style =
                MaterialTheme.typography.titleMedium
        )

        Spacer(
            modifier =
                Modifier.height(8.dp)
        )

        LazyRow(

            horizontalArrangement =
                Arrangement.spacedBy(8.dp)

        ) {

            items(availableTags) { tag ->

                FilterChip(

                    selected = tag.id in selectedTags,

                    onClick = {

                        onTagClick(tag.id)
                    },

                    label = {

                        Text(tag.name)
                    },

                    leadingIcon = {

                        Icon(

                            imageVector = tag.icon,

                            contentDescription = tag.name
                        )
                    }
                )
            }
        }

        Spacer(
            modifier =
                Modifier.height(16.dp)
        )

        Text("Seleccionadas")

        FlowRow {

            selectedTags.forEach { tagId ->

                val tag = availableTags.find {

                    it.id == tagId
                }

                tag?.let {

                    AssistChip(

                        onClick = {

                            onTagClick(it.id)
                        },

                        label = {

                            Text(it.name)
                        },

                        leadingIcon = {

                            Icon(

                                imageVector = it.icon,

                                contentDescription = it.name
                            )
                        }
                    )
                }
            }
        }
    }
}