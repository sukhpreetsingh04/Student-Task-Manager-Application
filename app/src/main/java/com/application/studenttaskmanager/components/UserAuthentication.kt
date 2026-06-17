package com.application.studenttaskmanager.components

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavController
import com.application.studenttaskmanager.R

@Composable
fun UserAuthentication(modifier: Modifier = Modifier, navController: NavController) {

    var userEmail by remember {
        mutableStateOf("")
    }

    var passwordVisible by remember { mutableStateOf(false) }

    var userPassword by remember {
        mutableStateOf("")
    }

    val toastContext = LocalContext.current

    var showDialog by rememberSaveable { mutableStateOf(false) }

    val myTextFieldColor = TextFieldDefaults.colors(
        focusedContainerColor = MaterialTheme.colorScheme.surface,
        unfocusedContainerColor = MaterialTheme.colorScheme.surfaceVariant,

        focusedTextColor = MaterialTheme.colorScheme.onSurface,
        unfocusedTextColor = MaterialTheme.colorScheme.onSurface,

        focusedIndicatorColor = Color(0xFFFFB74D),
        unfocusedIndicatorColor = MaterialTheme.colorScheme.secondary,

        cursorColor = Color(0xFFFFB74D),

        focusedLabelColor = Color(0xFFFFB74D),
        unfocusedLabelColor = MaterialTheme.colorScheme.onSurfaceVariant,

        focusedPlaceholderColor = MaterialTheme.colorScheme.onSurfaceVariant,
        unfocusedPlaceholderColor = MaterialTheme.colorScheme.onSurfaceVariant
    )

    Box(modifier = modifier.fillMaxSize()) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .blur(5.dp)
                .paint(
                    painter = painterResource(R.drawable.app_background),
                    contentScale = ContentScale.Crop
                )
                .background(Color.Black.copy(alpha = 0.35f))
        )

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 40.dp),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(R.drawable.app_logo),
                contentDescription = "App Logo",
                modifier = Modifier.size(150.dp),
                contentScale = ContentScale.Fit
            )
        }

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

        if (showDialog) {
            Dialog(
                onDismissRequest = {
                    showDialog = false
                }
            ) {
                Card(
                    shape = RoundedCornerShape(16.dp)
                ) {
                    Column(
                        modifier = Modifier
                            .padding(24.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "Welcome",
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Bold
                        )

                        Spacer(modifier = Modifier.height(20.dp))

                        TextField(
                            value = userEmail,
                            onValueChange = { userEmail = it },
                            label = { Text("Email") },
                            colors = myTextFieldColor,
                            shape = RoundedCornerShape(12.dp)
                        )

                        Spacer(modifier = Modifier.height(20.dp))

                        TextField(
                            value = userPassword,
                            onValueChange = { userPassword = it },
                            label = { Text(text = "Password") },
                            colors = myTextFieldColor,
                            shape = RoundedCornerShape(12.dp),
                            visualTransformation =
                                if (passwordVisible)
                                    VisualTransformation.None
                                else
                                    PasswordVisualTransformation(),

                            trailingIcon = {
                                IconButton(
                                    onClick = {
                                        passwordVisible = !passwordVisible
                                    }
                                ) {
                                    Icon(
                                        imageVector =
                                            if (passwordVisible)
                                                Icons.Default.Visibility
                                            else
                                                Icons.Default.VisibilityOff,
                                        contentDescription = null
                                    )
                                }
                            }
                        )

                        Spacer(modifier = Modifier.height(20.dp))

                        Button(
                            onClick = {
                                showDialog = false
                                    if(userEmail.isEmpty() || userPassword.isEmpty()) {

                                        Toast.makeText(toastContext, "Please enter all data", Toast.LENGTH_SHORT).show()
                                    } else {
                                        try {
                                            navController.navigate("DashBoard") {
                                            }
                                        } catch (e: IllegalArgumentException) {
                                            Toast.makeText(toastContext, "Please enter valid data", Toast.LENGTH_SHORT).show()
                                        }
                                    }
                            },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color(0xFFFFB74D),
                                contentColor = Color(0xFF1A1A1A)
                            )
                        ) {
                            Text("Sign Up")
                        }
                    }
                }
            }
        }
    }
}
