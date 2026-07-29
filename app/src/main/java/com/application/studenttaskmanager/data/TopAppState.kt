package com.application.studenttaskmanager.data

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable

data class TopAppState(
    val menuStatus: MutableState<Boolean>,
    val moreStatus: MutableState<Boolean>,
    val menuItems: List<String>,
    val moreItems: List<String>
)

@Composable
fun rememberTopAppState(): TopAppState {
    val menuStatus = rememberSaveable {
        mutableStateOf(false)
    }

    val moreStatus = rememberSaveable {
        mutableStateOf(false)
    }

    return TopAppState(

        menuStatus = menuStatus,

        moreStatus = moreStatus,

        menuItems = listOf(
            "All Tasks",
            "WishList",
            "Filter",
            "Sort",
            "Settings",
            "Logout"
        ),

        moreItems = listOf(
            "Personal",
            "DeadLines",
            "Your Progress",
            "Analytics and Weekly Reports"
        )
    )
}