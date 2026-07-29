package com.application.studenttaskmanager.components

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.res.stringArrayResource
import com.application.studenttaskmanager.R
import com.application.studenttaskmanager.data.TaskDraft
import com.application.studenttaskmanager.data.TaskItem
import java.util.Calendar
import java.util.TimeZone

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun rememberTaskDraft(task: TaskItem? = null): TaskDraft {

    val taskList = stringArrayResource(R.array.taskList)

    var description by rememberSaveable(task?.id) {
        mutableStateOf(task?.title ?: "")
    }

    val calendar = remember(task?.id) {
        Calendar.getInstance().apply {
            task?.dueAtMillis?.let {
                timeInMillis = it
            }
        }
    }

    val timePickerState = rememberTimePickerState(
        initialHour = calendar.get(Calendar.HOUR_OF_DAY),
        initialMinute = calendar.get(Calendar.MINUTE)
    )

    val itemPosition = remember(task?.id) {
        mutableIntStateOf(
            taskList.indexOf(task?.category).takeIf { it >= 0 } ?: 0
        )
    }

    var selectedDateMillis by rememberSaveable(task?.id) {
        mutableStateOf(task?.dueAtMillis)
    }

    return TaskDraft(
        title = description,
        category = taskList[itemPosition.intValue],
        dueAtMillis = selectedDateMillis?.let { millis ->
            val utcDate = Calendar.getInstance(TimeZone.getTimeZone("UTC"))
                .apply {
                    timeInMillis = millis
                }

            Calendar.getInstance()
                .apply {
                    set(Calendar.YEAR, utcDate.get(Calendar.YEAR))
                    set(Calendar.MONTH, utcDate.get(Calendar.MONTH))
                    set(
                        Calendar.DAY_OF_MONTH,
                        utcDate.get(Calendar.DAY_OF_MONTH)
                    )
                    set(Calendar.HOUR_OF_DAY, timePickerState.hour)
                    set(Calendar.MINUTE, timePickerState.minute)
                    set(Calendar.SECOND, 0)
                    set(Calendar.MILLISECOND, 0)
                }
                .timeInMillis
        }
    )
}