package com.application.studenttaskmanager.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.application.studenttaskmanager.components.common.ShowDialogApp
import com.application.studenttaskmanager.data.User

@Composable
fun WelcomeScreen(
    onLogin: (String, String) -> Result<User>,
    onRegister: (String, String, String) -> Result<User>,
    onAuthenticated: (User) -> Unit
) {

    var showDialog by rememberSaveable { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 300.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(
            modifier = Modifier
                .padding(10.dp)
                .background(Color.Transparent),
            text = "Student Task Manager",
            fontSize = 30.sp,
            fontWeight = FontWeight.ExtraBold,
            color = Color(0xFFFFFFFF),
            fontStyle = FontStyle.Italic
        )

        Spacer(modifier = Modifier.height(2.dp))

        Text(
            modifier = Modifier
                .padding(10.dp),
            text = ("Stay Organized, Create Tasks & Track Progress in one place."),
            fontSize = 18.sp,
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Medium,
            color = Color(0xFFFFFFFF),
        )

        Spacer(modifier = Modifier.height(30.dp))

        Button(
            onClick = {
                showDialog = true
            },
            modifier = Modifier.size(200.dp, 50.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFFFFB74D),
                contentColor = Color(0xFF1A1A1A)
            )
        ) {
            Text(text = "Get Started", fontSize = 20.sp, fontWeight = FontWeight.Bold)
        }
    }

    ShowDialogApp(
        showDialog = showDialog,
        onDismiss = { showDialog = false },
        onLogin = onLogin,
        onRegister = onRegister,
        onAuthenticated = {
            showDialog = false
            onAuthenticated(it)
        }
    )
}