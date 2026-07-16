@file:JvmName("TaskItemKt")

package com.application.studenttaskmanager.data

enum class TaskStatus {
    PENDING,
    OVERDUE,
    COMPLETED_ON_TIME,
    COMPLETED_LATE
}

fun TaskItem.getStatus(): TaskStatus {

    val now = System.currentTimeMillis()

    if (!isCompleted) {

        if (dueAtMillis == null)
            return TaskStatus.PENDING

        return if (now > dueAtMillis)
            TaskStatus.OVERDUE
        else
            TaskStatus.PENDING
    }

    if (dueAtMillis == null || completedAtMillis == null)
        return TaskStatus.COMPLETED_ON_TIME

    return if (completedAtMillis <= dueAtMillis)
        TaskStatus.COMPLETED_ON_TIME
    else
        TaskStatus.COMPLETED_LATE
}