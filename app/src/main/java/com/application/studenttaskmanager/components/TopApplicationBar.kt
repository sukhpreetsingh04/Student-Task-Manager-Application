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
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.text.font.FontWeight

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopApplicationBar(
    content: @Composable (PaddingValues) -> Unit
) {

    val menuStatus = remember() {
        mutableStateOf(false)
    }

    val menuItems = listOf(
        "All Tasks",
        "Personal",
        "Wishlist",
        "Your Favourites",
        "DeadLines",
        "Your Progress",
        "Analytics and Weekly Reports",
        "Settings",
        "Logout"
    )


    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Column {
                        Text(
                            text = "Dashboard",
                            color = Color(0xFFFFB74D),
                            fontSize = 26.sp,
                            fontWeight = FontWeight.ExtraBold
                        )
                    }
                },
                actions = {
                    IconButton(onClick = {}) {
                        Icon(Icons.Filled.Search, contentDescription = "Search icon")
                    }
                    IconButton(onClick = {menuStatus.value = true}) {
                        Icon(Icons.Filled.MoreVert, contentDescription = "More icon")

                        DropdownMenu(
                            containerColor =  Color(0xFF1E1E1E),
                            expanded = menuStatus.value,
                            onDismissRequest = { menuStatus.value = false }) {
                            menuItems.forEach { DropdownMenuItem(text = { Text(text = it, color = Color(0xFFFFB74D)) }, onClick = {
                                menuStatus.value = false
                            }) }
                        }
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFF1E1E1E),
                    titleContentColor = Color.White,
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