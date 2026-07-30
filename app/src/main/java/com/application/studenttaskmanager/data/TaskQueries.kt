package com.application.studenttaskmanager.data

import android.content.ContentValues
import android.content.Context
import com.application.studenttaskmanager.data.mapper.toTaskItem

class TaskQueries(context: Context) {
    private val appContext = context.applicationContext
    private val dbHelper = StudentDatabaseHelper(appContext)

    fun tasksForUser(userId: Long): List<TaskItem> {
        val db = dbHelper.readableDatabase
        val tasks = mutableListOf<TaskItem>()

        db.rawQuery(
            """
            SELECT
            id,
            user_id,
            title,
            description,
            category,
            due_at,
            is_completed,
            created_at,
            completed_at
            FROM tasks
            WHERE user_id = ?
            ORDER BY is_completed ASC, COALESCE(due_at, created_at) ASC
            """.trimIndent(), arrayOf(userId.toString())
        ).use { cursor ->
            while (cursor.moveToNext()) {
                tasks.add(cursor.toTaskItem())
            }
        }

        return tasks
    }

    fun addTask(userId: Long, draft: TaskDraft): TaskItem {
        val values = ContentValues().apply {
            put("user_id", userId)
            put("title", draft.title.trim())
            put("description", draft.description.trim())
            put("category", draft.category)
            if (draft.dueAtMillis != null) {
                put("due_at", draft.dueAtMillis)
            } else {
                putNull("due_at")
            }
            put("is_completed", 0)
            put("created_at", System.currentTimeMillis())
        }

        val id = dbHelper.writableDatabase.insertOrThrow("tasks", null, values)
        return getTaskById(id) ?: error("Task was not saved")
    }

    fun updateTask(taskId: Long, draft: TaskDraft) {
        val values = ContentValues().apply {
            put("title", draft.title.trim())
            put("description", draft.description.trim())
            put("category", draft.category)

            if (draft.dueAtMillis != null) {
                put("due_at", draft.dueAtMillis)
            } else {
                putNull("due_at")
            }
        }

        dbHelper.writableDatabase.update(
            "tasks", values, "id = ?", arrayOf(taskId.toString())
        )
    }

    fun setTaskCompleted(taskId: Long, completed: Boolean) {
        val values = ContentValues().apply {

            put("is_completed", if (completed) 1 else 0)

            if (completed) {
                put("completed_at", System.currentTimeMillis())
            } else {
                putNull("completed_at")
            }
        }
        dbHelper.writableDatabase.update(
            "tasks", values, "id = ?", arrayOf(taskId.toString())
        )
    }

    fun deleteTask(taskId: Long) {
        dbHelper.writableDatabase.delete("tasks", "id = ?", arrayOf(taskId.toString()))
    }

    fun getTaskById(taskId: Long): TaskItem? {
        dbHelper.readableDatabase.rawQuery(
            """
        SELECT
            id,
            user_id,
            title,
            description,
            category,
            due_at,
            is_completed,
            created_at,
            completed_at
        FROM tasks
        WHERE id = ?
        """.trimIndent(), arrayOf(taskId.toString())
        ).use { cursor ->
            if (!cursor.moveToFirst()) return null
            return cursor.toTaskItem()
        }
    }
}