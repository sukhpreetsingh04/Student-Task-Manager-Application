package com.application.studenttaskmanager.components.input

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuAnchorType
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringArrayResource
import com.application.studenttaskmanager.R
import com.application.studenttaskmanager.data.TaskDraft
import com.application.studenttaskmanager.ui.design.AppTextFieldColors

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExposedDropDownMenu(
    draft: TaskDraft,
    onDraftChange: (TaskDraft) -> Unit
) {

    var expanded by rememberSaveable { mutableStateOf(false) }

    val taskList = stringArrayResource(R.array.taskList)

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = !expanded }
    ) {
        OutlinedTextField(
            value = draft.category.ifBlank { "Select Category" },
            onValueChange = {},
            readOnly = true,
            modifier = Modifier
                .menuAnchor(ExposedDropdownMenuAnchorType.PrimaryNotEditable)
                .fillMaxWidth(),
            colors = AppTextFieldColors.default(),
            trailingIcon = {
                ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
            }
        )

        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            taskList.forEach { category ->
                DropdownMenuItem(
                    text = { Text(category) },
                    onClick = {
                        onDraftChange(
                            draft.copy(category = category)
                        )
                        expanded = false
                    }
                )
            }
        }
    }
}