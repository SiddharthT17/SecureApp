package com.dummyapp.sharesecureapp.view

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.dummyapp.sharesecureapp.DelayedNavigationScreen
import com.dummyapp.sharesecureapp.ImageFromDrawable
import com.dummyapp.sharesecureapp.R
import com.dummyapp.sharesecureapp.Screen
import com.dummyapp.sharesecureapp.SecureStorage
import com.dummyapp.sharesecureapp.data.UserData

// Login Screen
@Composable
fun LoginScreen(
    users: List<UserData>,
    navController: NavHostController
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var errorMessage by remember { mutableStateOf("") }
    val (passwordVisible, setPasswordVisible) = remember { mutableStateOf(true) }
    var navigateScreen by remember { mutableStateOf("") }
    var emailError by remember { mutableStateOf<String?>(null) }
    var passwordError by remember { mutableStateOf<String?>(null) }
    val context = LocalContext.current

    // Validation function
    fun validateFields(): Boolean {
        var isValid = true

        // Email validation
        if (email.isBlank() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            emailError = "Invalid email address"
            isValid = false
        } else {
            emailError = null
        }

        // Password validation
        if (password.isBlank() || password.length < 6) {
            passwordError = "Password must be at least 6 characters"
            isValid = false
        } else {
            passwordError = null
        }

        return isValid
    }

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            //Text("Login", style = MaterialTheme.typography.h5)
            ImageFromDrawable()
            Text(
                text = "iBoxt Secure Fleet", //ShareSecure
                style = MaterialTheme.typography.h4,
                fontStyle = FontStyle.Italic
            )

            Spacer(modifier = Modifier.height(24.dp))

            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                label = { Text("Email") },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(8.dp),
                keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Next),
                leadingIcon = {
                    Icon(
                        painter = painterResource(id = R.drawable.user_email), // Replace with your user icon resource
                        contentDescription = ""
                    )
                }
            )

            Spacer(modifier = Modifier.height(8.dp))
            emailError?.let { Text(it, color = Color.Red, fontSize = 12.sp) }
            Spacer(modifier = Modifier.height(16.dp))

            //Spacer(modifier = Modifier.height(8.dp))
            //TextField(value = email, onValueChange = { email = it }, label = { Text("Email") })
            //Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                label = { Text("Password") },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(8.dp),
                visualTransformation = if (!passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done),
                leadingIcon = {
                    Icon(
                        painter = painterResource(id = R.drawable.password_lock), // Replace with your user icon resource
                        contentDescription = ""
                    )
                },
                trailingIcon = {
                    IconButton(onClick = { setPasswordVisible(!passwordVisible) }) {
                        Icon(
                            painter = painterResource(id = if (passwordVisible) android.R.drawable.ic_menu_view else android.R.drawable.ic_menu_close_clear_cancel), // Toggle visibility icon
                            contentDescription = if (passwordVisible) "Hide Password" else "Show Password"
                        )
                    }
                }
            )

            Spacer(modifier = Modifier.height(8.dp))
            passwordError?.let { Text(it, color = Color.Red, fontSize = 12.sp) }
            Spacer(modifier = Modifier.height(16.dp))
            Button(
                onClick = {
                    if (validateFields()) {
                        val (savedEmail, savedPassword) = SecureStorage.getUserDetails(context, email, password)
                        if (savedEmail != null && savedPassword != null) {
                            navigateScreen = Screen.IDVerificationScreen.route
                        } else {
                            errorMessage = "Invalid email or password"
                        }
//                        val user = users.find { it.email == email && it.password == password }
//                        if (user != null) {
//                            //navController.navigate(Screen.ConfirmationScreen.route)
//                            navigateScreen = Screen.ConfirmationScreen.route
//                        } else {
//                            errorMessage = "Invalid email or password"
//                        }
                    }
                }
            ) {
                Text("Sign In", color = Color.White, fontWeight = FontWeight.Medium)
            }

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                "Not registered?", //Or Sign up with
                color = Color.DarkGray,
                fontWeight = FontWeight.Bold,
                fontStyle = FontStyle.Italic
            )

            Spacer(modifier = Modifier.height(8.dp))

            Row {
                Button(onClick = {
                    navController.navigate("createAccount/true")
                }) {
                    Text(
                        "Personal Account",
                        color = Color.White,
                        fontWeight = FontWeight.Medium
                    )
                }
                //Text("Or", color = Color.White, fontWeight = FontWeight.Medium)
                Spacer(modifier = Modifier.width(10.dp))
                Button(onClick = {
                    navController.navigate("createAccount/false")
                }) {
                    Text(
                        "Business Account",
                        color = Color.White,
                        fontWeight = FontWeight.Medium
                    )
                }
            }
            if (errorMessage.isNotEmpty()) {
                Spacer(modifier = Modifier.height(8.dp))
                Text(errorMessage, color = MaterialTheme.colors.error)
            }
        }
        if (navigateScreen.isNotEmpty()) {
            DelayedNavigationScreen(navController, navigateScreen)
        }
    }
}