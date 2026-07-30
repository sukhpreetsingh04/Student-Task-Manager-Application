package com.application.studenttaskmanager.components.common

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
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
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import com.application.studenttaskmanager.data.rememberTopAppState
import com.application.studenttaskmanager.ui.design.AppTextFieldColors

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopApplicationBar(
    onMenuItemSelected: (String) -> Unit = {},
    onMoreItemSelected: (String) -> Unit = {},
    content: @Composable (PaddingValues) -> Unit
) {

    val topState = rememberTopAppState()

    var isSearching by remember { mutableStateOf(false) }

    var searchQuery by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    if (isSearching) {
                        TextField(
                            value = searchQuery,
                            onValueChange = { searchQuery = it },
                            placeholder = { Text("Search...") },
                            modifier = Modifier.fillMaxWidth(),
                            singleLine = true,
                            colors = AppTextFieldColors.topBarSearch()
                        )
                    } else {
                        Column {
                            Text(
                                text = "Dashboard",
                                fontSize = 26.sp,
                                fontWeight = FontWeight.ExtraBold
                            )
                        }
                    }
                },
                actions = {
                    IconButton(
                        onClick = { topState.menuStatus.value = true }
                    ) {
                        Icon(
                            Icons.Filled.Menu,
                            contentDescription = "Menu Icon"
                        )

                        DropdownMenu(
                            containerColor = MaterialTheme.colorScheme.surface,
                            expanded = topState.menuStatus.value,
                            onDismissRequest = {
                                topState.menuStatus.value = false
                            }
                        ) {
                            topState.menuItems.forEach {
                                DropdownMenuItem(
                                    text = {
                                        Text(
                                            text = it,
                                            color = Color(0xFFFFB74D)
                                        )
                                    },
                                    onClick = {
                                        topState.menuStatus.value = false
                                        onMenuItemSelected(it)
                                    }
                                )
                            }
                        }
                    }

                    IconButton(
                        onClick = { isSearching = !isSearching }
                    ) {
                        Icon(
                            Icons.Filled.Search,
                            contentDescription = "Search icon"
                        )
                    }

                    IconButton(
                        onClick = { topState.moreStatus.value = true }
                    ) {
                        Icon(
                            Icons.Filled.MoreVert,
                            contentDescription = "More icon"
                        )

                        DropdownMenu(
                            containerColor = MaterialTheme.colorScheme.surface,
                            expanded = topState.moreStatus.value,
                            onDismissRequest = {
                                topState.moreStatus.value = false
                            }
                        ) {
                            topState.moreItems.forEach {
                                DropdownMenuItem(
                                    text = {
                                        Text(
                                            text = it,
                                            color = Color(0xFFFFB74D)
                                        )
                                    },
                                    onClick = {
                                        topState.moreStatus.value = false
                                        onMoreItemSelected(it)
                                    }
                                )
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
