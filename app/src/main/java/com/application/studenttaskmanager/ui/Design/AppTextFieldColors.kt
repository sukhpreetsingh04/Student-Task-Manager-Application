package com.application.studenttaskmanager.ui.Design

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

object AppTextFieldColors {

    @Composable
    fun colors() = TextFieldDefaults.colors(
        focusedContainerColor = MaterialTheme.colorScheme.surface,
        unfocusedContainerColor = MaterialTheme.colorScheme.surfaceVariant,
        focusedTextColor = MaterialTheme.colorScheme.onSurface,
        unfocusedTextColor = MaterialTheme.colorScheme.onSurface,
        focusedIndicatorColor = Color(0xFFFFB74D),
        unfocusedIndicatorColor = MaterialTheme.colorScheme.secondary,
        cursorColor = Color(0xFFFFB74D),
        focusedLabelColor = Color(0xFFFFB74D),
        unfocusedLabelColor = MaterialTheme.colorScheme.onSurfaceVariant,
        focusedPlaceholderColor = MaterialTheme.colorScheme.onSurfaceVariant,
        unfocusedPlaceholderColor = MaterialTheme.colorScheme.onSurfaceVariant
    )

}