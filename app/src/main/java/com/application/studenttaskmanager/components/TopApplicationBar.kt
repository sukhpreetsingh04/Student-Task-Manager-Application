package com.application.studenttaskmanager.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.sp
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.text.font.FontWeight

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopApplicationBar(
    content: @Composable (PaddingValues) -> Unit
) {

    val menuStatus = rememberSaveable {
        mutableStateOf(false)
    }

    val moreStatus = rememberSaveable {
        mutableStateOf(false)
    }

    val menuItems = listOf(
        "All Tasks",
        "WishList",
        "Filter",
        "Sort",
        "Settings",
        "Logout"
    )

    val moreItems = listOf(
        "Personal",
        "DeadLines",
        "Your Progress",
        "Analytics and Weekly Reports"
    )

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Column {
                        Text(
                            text = "Dashboard",
                            fontSize = 26.sp,
                            fontWeight = FontWeight.ExtraBold
                        )
                    }
                },
                actions = {
                    IconButton(onClick = {menuStatus.value = true}) {
                        Icon(
                            Icons.Filled.Menu,
                            contentDescription = "Menu Icon"
                        )
                        DropdownMenu(
                            containerColor = MaterialTheme.colorScheme.surface,
                            expanded = menuStatus.value,
                            onDismissRequest = { menuStatus.value = false }) {
                            menuItems.forEach {
                                DropdownMenuItem(text = {
                                    Text(
                                        text = it,
                                        color = Color(0xFFFFB74D)
                                    )
                                }, onClick = {
                                    menuStatus.value = false
                                })
                            }
                        }
                    }
                    IconButton(onClick = {}) {
                        Icon(
                            Icons.Filled.Search,
                            contentDescription = "Search icon"
                        )
                    }
                    IconButton(onClick = { moreStatus.value = true }) {
                        Icon(
                            Icons.Filled.MoreVert,
                            contentDescription = "More icon"
                        )
                        DropdownMenu(
                            containerColor = MaterialTheme.colorScheme.surface,
                            expanded = moreStatus.value,
                            onDismissRequest = { moreStatus.value = false }) {
                            moreItems.forEach {
                                DropdownMenuItem(text = {
                                    Text(
                                        text = it,
                                        color = Color(0xFFFFB74D)
                                    )
                                }, onClick = {
                                    moreStatus.value = false
                                })
                            }
                        }
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surface,
                    titleContentColor = Color(0xFFFFB74D),
                    actionIconContentColor = Color(0xFFFFB74D),
                    navigationIconContentColor = Color(0xFFFFB74D)
                )
            )
        },
        content = { paddingValues ->
            content(paddingValues)
        }
    )
}