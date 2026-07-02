package com.application.studenttaskmanager.data

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteConstraintException
import androidx.core.content.edit
import java.security.MessageDigest

class StudentRepository(context: Context) {
    private val appContext = context.applicationContext
    private val dbHelper = StudentDatabaseHelper(appContext)
    private val preferences =
        appContext.getSharedPreferences("student_task_manager_session", Context.MODE_PRIVATE)

    fun currentUser(): User? {
        val userId = preferences.getLong(KEY_USER_ID, 0L)
        return if (userId > 0L) getUserById(userId) else null
    }

    fun register(name: String, email: String, password: String): Result<User> {
        val cleanName = name.trim().ifBlank { "Student" }
        val cleanEmail = email.trim().lowercase()

        if (!cleanEmail.contains("@") || password.length < 6) {
            return Result.failure(
                IllegalArgumentException("Use a valid email and a password with at least 6 characters")
            )
        }

        return try {
            val values = ContentValues().apply {
                put("name", cleanName)
                put("email", cleanEmail)
                put("password_hash", passwordHash(cleanEmail, password))
                put("created_at", System.currentTimeMillis())
            }

            val id = dbHelper.writableDatabase.insertOrThrow("users", null, values)
            val user = User(id = id, name = cleanName, email = cleanEmail)
            saveSession(user)
            Result.success(user)
        } catch (_: SQLiteConstraintException) {
            Result.failure(IllegalArgumentException("This email is already registered"))
        } catch (exception: Exception) {
            Result.failure(exception)
        }
    }

    fun login(email: String, password: String): Result<User> {
        val cleanEmail = email.trim().lowercase()
        val expectedHash = passwordHash(cleanEmail, password)
        val db = dbHelper.readableDatabase

        db.rawQuery(
            "SELECT id, name, email FROM users WHERE email = ? AND password_hash = ?",
            arrayOf(cleanEmail, expectedHash)
        ).use { cursor ->
            if (!cursor.moveToFirst()) {
                return Result.failure(IllegalArgumentException("Invalid email or password"))
            }

            val user = User(
                id = cursor.getLong(cursor.getColumnIndexOrThrow("id")),
                name = cursor.getString(cursor.getColumnIndexOrThrow("name")),
                email = cursor.getString(cursor.getColumnIndexOrThrow("email"))
            )
            saveSession(user)
            return Result.success(user)
        }
    }

    fun logout() {
        preferences.edit { clear() }
    }

    fun tasksForUser(userId: Long): List<TaskItem> {
        val db = dbHelper.readableDatabase
        val tasks = mutableListOf<TaskItem>()

        db.rawQuery(
            """
            SELECT id, user_id, title, category, due_at, is_completed, created_at
            FROM tasks
            WHERE user_id = ?
            ORDER BY is_completed ASC, COALESCE(due_at, created_at) ASC
            """.trimIndent(),
            arrayOf(userId.toString())
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

    fun setTaskCompleted(taskId: Long, completed: Boolean) {
        val values = ContentValues().apply {
            put("is_completed", if (completed) 1 else 0)
        }
        dbHelper.writableDatabase.update(
            "tasks",
            values,
            "id = ?",
            arrayOf(taskId.toString())
        )
    }

    fun deleteTask(taskId: Long) {
        dbHelper.writableDatabase.delete("tasks", "id = ?", arrayOf(taskId.toString()))
    }

    private fun saveSession(user: User) {
        preferences.edit { putLong(KEY_USER_ID, user.id) }
    }

    private fun getUserById(userId: Long): User? {
        dbHelper.readableDatabase.rawQuery(
            "SELECT id, name, email FROM users WHERE id = ?",
            arrayOf(userId.toString())
        ).use { cursor ->
            if (!cursor.moveToFirst()) return null
            return User(
                id = cursor.getLong(cursor.getColumnIndexOrThrow("id")),
                name = cursor.getString(cursor.getColumnIndexOrThrow("name")),
                email = cursor.getString(cursor.getColumnIndexOrThrow("email"))
            )
        }
    }

    private fun getTaskById(taskId: Long): TaskItem? {
        dbHelper.readableDatabase.rawQuery(
            """
            SELECT id, user_id, title, category, due_at, is_completed, created_at
            FROM tasks
            WHERE id = ?
            """.trimIndent(),
            arrayOf(taskId.toString())
        ).use { cursor ->
            if (!cursor.moveToFirst()) return null
            return cursor.toTaskItem()
        }
    }

    private fun android.database.Cursor.toTaskItem(): TaskItem {
        val dueColumn = getColumnIndexOrThrow("due_at")
        return TaskItem(
            id = getLong(getColumnIndexOrThrow("id")),
            userId = getLong(getColumnIndexOrThrow("user_id")),
            title = getString(getColumnIndexOrThrow("title")),
            category = getString(getColumnIndexOrThrow("category")),
            dueAtMillis = if (isNull(dueColumn)) null else getLong(dueColumn),
            isCompleted = getInt(getColumnIndexOrThrow("is_completed")) == 1,
            createdAtMillis = getLong(getColumnIndexOrThrow("created_at"))
        )
    }

    private fun passwordHash(email: String, password: String): String {
        val bytes = MessageDigest.getInstance("SHA-256")
            .digest("$email:$password".toByteArray())
        return bytes.joinToString(separator = "") { "%02x".format(it) }
    }

    companion object {
        private const val KEY_USER_ID = "user_id"
    }
}
