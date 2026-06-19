package com.application.studenttaskmanager.components

import android.content.Context
import java.io.ObjectInputStream
import java.io.ObjectOutputStream
import kotlin.collections.emptyList

const val File_Name = "studenttaskmanager.dat"

fun writeData(tasks: List<String>, context : Context) {
    context.openFileOutput(File_Name, Context.MODE_PRIVATE).use { fos ->
        ObjectOutputStream(fos).use { oos ->
            oos.writeObject(tasks)
        }
    }
}

fun readData(context: Context) : List<String> {
    return try {
        context.openFileInput(File_Name).use { fis ->
            ObjectInputStream(fis).use { ois ->
                @Suppress("UNCHECKED_CAST")
                ois.readObject() as List<String>
            }
        }
    } catch (e: Exception) {
        emptyList()
    }
}