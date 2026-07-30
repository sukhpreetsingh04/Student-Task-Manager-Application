package com.application.studenttaskmanager.components.common

import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.application.studenttaskmanager.data.User
import com.application.studenttaskmanager.ui.design.AppTextFieldColors

@Composable
fun ShowDialogApp(
    showDialog: Boolean,
    onDismiss: () -> Unit,
    onLogin: (String, String) -> Result<User>,
    onRegister: (String, String, String) -> Result<User>,
    onAuthenticated: (User) -> Unit
) {

    val userName = rememberSaveable {
        mutableStateOf("")
    }

    val userEmail = rememberSaveable {
        mutableStateOf("")
    }

    var passwordVisible by rememberSaveable { mutableStateOf(false) }

    val userPassword = rememberSaveable {
        mutableStateOf("")
    }

    val toastContext = LocalContext.current

    var isLoginMode by rememberSaveable { mutableStateOf(true) }

    if (showDialog) {
        Dialog(
            onDismissRequest = onDismiss
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
                        text = if (isLoginMode) "Welcome Back" else "Create Account",
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold
                    )

                    Spacer(modifier = Modifier.height(20.dp))

                    if (!isLoginMode) {
                        TextField(
                            value = userName.value,
                            onValueChange = { userName.value = it },
                            label = { Text("User Name") },
                            colors = AppTextFieldColors.default(),
                            shape = RoundedCornerShape(12.dp)
                        )

                        Spacer(modifier = Modifier.height(20.dp))
                    }

                    TextField(
                        value = userEmail.value,
                        onValueChange = { userEmail.value = it },
                        label = { Text("Email") },
                        colors = AppTextFieldColors.default(),
                        shape = RoundedCornerShape(12.dp)
                    )

                    Spacer(modifier = Modifier.height(20.dp))

                    TextField(
                        value = userPassword.value,
                        onValueChange = { userPassword.value = it },
                        label = { Text(text = "Password") },
                        colors = AppTextFieldColors.default(),
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
                            val result =
                                if (isLoginMode) {
                                    onLogin(userEmail.value, userPassword.value)
                                } else {
                                    onRegister(userName.value, userEmail.value, userPassword.value)
                                }

                            result
                                .onSuccess { user ->
                                    onDismiss()
                                    onAuthenticated(user)
                                }
                                .onFailure { error ->
                                    Toast.makeText(
                                        toastContext,
                                        error.message ?: "Please enter valid data",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFFFFB74D),
                            contentColor = Color(0xFF1A1A1A)
                        )
                    ) {
                        Text(if (isLoginMode) "Login" else "Sign Up")
                    }

                    TextButton(
                        onClick = {
                            isLoginMode = !isLoginMode
                        }
                    ) {
                        Text(
                            text = if (isLoginMode) "New student? Sign up" else "Already registered? Login",
                            color = Color(0xFFFFB74D)
                        )
                    }
                }
            }
        }
    }

}