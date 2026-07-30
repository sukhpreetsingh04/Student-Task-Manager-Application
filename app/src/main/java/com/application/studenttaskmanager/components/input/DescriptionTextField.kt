package com.application.studenttaskmanager.components.input

import android.app.Activity
import android.content.Intent
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.core.content.ContextCompat
import android.Manifest
import android.content.pm.PackageManager
import androidx.compose.ui.platform.LocalContext
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Mic
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.application.studenttaskmanager.data.TaskDraft
import com.application.studenttaskmanager.ui.design.AppTextFieldColors
import android.speech.RecognizerIntent
import androidx.activity.result.contract.ActivityResultContracts
@Composable
fun DescriptionTextField(
    draft: TaskDraft,
    onDraftChange: (TaskDraft) -> Unit
) {

    val context = LocalContext.current

    val speechLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val spokenText = result.data
                ?.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS)
                ?.firstOrNull()

            if (!spokenText.isNullOrBlank()) {
                onDraftChange(
                    draft.copy(
                        title = spokenText
                    )
                )
            }
        }
    }

    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { granted ->
        if (granted) {
            launchSpeechRecognizer(speechLauncher)
        }
    }

    OutlinedTextField(
        value = draft.title,
        onValueChange = {
            onDraftChange(
                draft.copy(title = it)
            )
        },
        modifier = Modifier.fillMaxWidth(),
        label = { Text("Enter Task Here") },
        colors = AppTextFieldColors.default(),
        shape = RoundedCornerShape(12.dp),
        trailingIcon = {
            IconButton(
                onClick = {
                    if (
                        ContextCompat.checkSelfPermission(
                            context,
                            Manifest.permission.RECORD_AUDIO
                        ) == PackageManager.PERMISSION_GRANTED
                    ) {
                        launchSpeechRecognizer(speechLauncher)
                    } else {
                        permissionLauncher.launch(Manifest.permission.RECORD_AUDIO)
                    }
                }
            ) {
                Icon(
                    imageVector = Icons.Filled.Mic,
                    contentDescription = "Voice input",
                    tint = Color(0xFFFFB74D)
                )
            }
        }
    )
}

private fun launchSpeechRecognizer(
    launcher: androidx.activity.result.ActivityResultLauncher<Intent>
) {
    val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH).apply {
        putExtra(
            RecognizerIntent.EXTRA_LANGUAGE_MODEL,
            RecognizerIntent.LANGUAGE_MODEL_FREE_FORM
        )

        putExtra(
            RecognizerIntent.EXTRA_LANGUAGE,
            java.util.Locale.getDefault()
        )

        putExtra(
            RecognizerIntent.EXTRA_PROMPT,
            "Speak your task..."
        )
    }

    launcher.launch(intent)
}